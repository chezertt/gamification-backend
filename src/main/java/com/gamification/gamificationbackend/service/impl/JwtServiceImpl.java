package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.config.JwtConfig;
import com.gamification.gamificationbackend.dto.request.InvitationRequestDto;
import com.gamification.gamificationbackend.dto.system.SecurityTokenPairDto;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.entity.Player;
import com.gamification.gamificationbackend.entity.User;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.enumeration.JwtClaim;
import com.gamification.gamificationbackend.enumeration.JwtTokenType;
import com.gamification.gamificationbackend.enumeration.UserRole;
import com.gamification.gamificationbackend.exception.InternalServerErrorException;
import com.gamification.gamificationbackend.exception.UnauthorizedException;
import com.gamification.gamificationbackend.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtConfig jwtConfig;

    private final HttpServletRequest request;

    private final EntityManager entityManager;

    @Override
    public String validateTokenAndExtractEmail(String token, JwtTokenType type) {
        Claims tokenClaims = validateTokenAndExtractClaims(token, type);
        return tokenClaims.getSubject();
    }

    private Claims validateTokenAndExtractClaims(String token, JwtTokenType type) {
        String secret = getTokenSecretByType(type);

        Claims tokenClaims;
        try {
            tokenClaims = Jwts.parser()
                    .verifyWith(getSignKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorType.TECH, "Invalid token");
        }

        if (tokenClaims.getExpiration().before(new Date())) {
            throw new UnauthorizedException(ErrorType.TECH, "Invalid token");
        }
        return tokenClaims;
    }


    @Override
    public SecurityTokenPairDto generateSecurityTokenPairForCompany(String email, Long userId, Long companyId) {
        return generateSecurityTokenPair(email,
                Map.of(JwtClaim.ROLE_NAME.getValue(), UserRole.ROLE_COMPANY.name(),
                        JwtClaim.USER_ID.getValue(), userId,
                        JwtClaim.COMPANY_ID.getValue(), companyId));
    }

    @Override
    public SecurityTokenPairDto generateSecurityTokenPairForPlayer(String email, Long userId, Long companyId, Long playerId) {
        return generateSecurityTokenPair(email,
                Map.of(JwtClaim.ROLE_NAME.getValue(), UserRole.ROLE_PLAYER.name(),
                        JwtClaim.USER_ID.getValue(), userId,
                        JwtClaim.COMPANY_ID.getValue(), companyId,
                        JwtClaim.PLAYER_ID.getValue(), playerId));
    }

    private SecurityTokenPairDto generateSecurityTokenPair(String email, Map<String, Object> claims) {
        String accessToken = createToken(email, claims, Optional.of(jwtConfig.getAccessTokenLifetimeInMs()), jwtConfig.getAccessTokenSecret());
        String refreshToken = createToken(email, claims, Optional.of(jwtConfig.getRefreshTokenLifetimeInMs()), jwtConfig.getRefreshTokenSecret());
        return SecurityTokenPairDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String generateInvitationToken(String email, InvitationRequestDto invitationRequestDto, Long companyId) {
        Map<String, Object> claims = Map.of(
                JwtClaim.PLAYER_FIRST_NAME.getValue(), invitationRequestDto.getPlayerFirstName(),
                JwtClaim.PLAYER_LAST_NAME.getValue(), invitationRequestDto.getPlayerLastName(),
                JwtClaim.PLAYER_TITLE.getValue(), invitationRequestDto.getPlayerTitle(),
                JwtClaim.COMPANY_ID.getValue(), companyId);
        return createToken(email, claims, Optional.empty(), jwtConfig.getInvitationTokenSecret());
    }

    @Override
    public Player validateInvitationTokenAndExtractPlayerUserData(String invitationToken) {
        Claims tokenClaims = validateTokenAndExtractClaims(invitationToken, JwtTokenType.INVITATION);
        return Player.builder()
                .firstName(tokenClaims.get(JwtClaim.PLAYER_FIRST_NAME.getValue(), String.class))
                .lastName(tokenClaims.get(JwtClaim.PLAYER_LAST_NAME.getValue(), String.class))
                .title(tokenClaims.get(JwtClaim.PLAYER_TITLE.getValue(), String.class))
                .user(User.builder()
                        .email(tokenClaims.getSubject())
                        .company(entityManager.getReference(Company.class, tokenClaims.get(JwtClaim.COMPANY_ID.getValue(), Long.class)))
                        .build())
                .build();
    }

    @Override
    public int getRefreshTokenLifetimeInSeconds() {
        return Math.toIntExact(jwtConfig.getRefreshTokenLifetimeInMs() / 1000);
    }

    @Override
    public String extractAccessTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasLength(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    @Override
    public <T> T extractClaimValueFromAccessToken(JwtClaim claim, Class<T> claimClass) {
        try {
            String accessToken = extractAccessTokenFromRequest(request);
            Claims claims = validateTokenAndExtractClaims(accessToken, JwtTokenType.ACCESS);
            return claims.get(claim.getValue(), claimClass);
        } catch (Exception e) {
            throw new InternalServerErrorException(ErrorType.TECH,
                    String.format("Не получилось достать данные из access токена по ключу %s типа %s",
                            claim.getValue(), claimClass.getSimpleName()));
        }
    }

    @Override
    public Long getCompanyId() {
        return extractClaimValueFromAccessToken(JwtClaim.COMPANY_ID, Long.class);
    }

    @Override
    public Long getPlayerId() {
        return extractClaimValueFromAccessToken(JwtClaim.PLAYER_ID, Long.class);
    }

    private String createToken(String email, Map<String, Object> claims, Optional<Long> lifetimeInMs, String secret) {
        long nowInMs = System.currentTimeMillis();
        Date expiration = lifetimeInMs.isPresent()
                ? new Date(nowInMs + lifetimeInMs.get())
                : new Date(Long.MAX_VALUE);
        return Jwts.builder()
                .subject(email)
                .claims(claims)
                .issuedAt(new Date(nowInMs))
                .expiration(expiration)
                .signWith(getSignKey(secret))
                .compact();
    }

    private SecretKey getSignKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private String getTokenSecretByType(JwtTokenType type) {
        return switch (type) {
            case ACCESS -> jwtConfig.getAccessTokenSecret();
            case REFRESH -> jwtConfig.getRefreshTokenSecret();
            case INVITATION -> jwtConfig.getInvitationTokenSecret();
        };
    }
}
