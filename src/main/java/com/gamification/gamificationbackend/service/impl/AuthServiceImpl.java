package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.request.AuthRequestDto;
import com.gamification.gamificationbackend.dto.request.CompanyRegistrationDto;
import com.gamification.gamificationbackend.dto.request.PlayerRegistrationDto;
import com.gamification.gamificationbackend.dto.response.AuthResponseDto;
import com.gamification.gamificationbackend.dto.system.SecurityTokenPairDto;
import com.gamification.gamificationbackend.entity.User;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.enumeration.JwtTokenType;
import com.gamification.gamificationbackend.enumeration.UserRole;
import com.gamification.gamificationbackend.exception.BadRequestException;
import com.gamification.gamificationbackend.exception.UnauthorizedException;
import com.gamification.gamificationbackend.service.AuthService;
import com.gamification.gamificationbackend.service.JwtService;
import com.gamification.gamificationbackend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final JwtService jwtService;

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    private final PasswordEncoder passwordEncoder;

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    @Override
    public ResponseEntity<AuthResponseDto> login(AuthRequestDto authRequestDto) {
        String userEmail = authRequestDto.getEmail();
        UserDetails userDetails = userService.loadUserByUsername(userEmail);
        if (passwordEncoder.matches(authRequestDto.getPassword(), userDetails.getPassword())) {
            return generateTokenPairAndGetResponse(userDetails, userEmail);
        } else {
            throw new BadRequestException(ErrorType.CLIENT, "Некорректная почта или пароль.");
        }
    }

    @Override
    public ResponseEntity<AuthResponseDto> registerCompany(CompanyRegistrationDto companyRegistrationDto) {
        UserDetails userDetails = userService.saveCompanyUserAndGet(companyRegistrationDto);
        return generateTokenPairAndGetResponse(userDetails, userDetails.getUsername());
    }

    @Override
    public ResponseEntity<AuthResponseDto> registerPlayer(PlayerRegistrationDto playerRegistrationDto) {
        UserDetails userDetails = userService.savePlayerUserAndGet(playerRegistrationDto);
        return generateTokenPairAndGetResponse(userDetails, userDetails.getUsername());
    }

    @Override
    public ResponseEntity<AuthResponseDto> refresh() {
        String refreshToken = extractRefreshTokenFromCookies();
        String userEmail = jwtService.validateTokenAndExtractEmail(refreshToken, JwtTokenType.REFRESH);
        UserDetails userDetails = userService.loadUserByUsername(userEmail);
        return generateTokenPairAndGetResponse(userDetails, userEmail);
    }

    private String extractRefreshTokenFromCookies() {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            throw new UnauthorizedException(ErrorType.TECH, "No cookie with refresh token");
        }
        return Arrays.stream(cookies)
                .filter(cookie -> REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException(ErrorType.TECH, "No cookie with refresh token"))
                .getValue();
    }

    private ResponseEntity<AuthResponseDto> generateTokenPairAndGetResponse(UserDetails userDetails, String userEmail) {
        User user = (User) userDetails;
        Long companyId = user.getCompany().getId();
        UserRole userRole = user.getRole();
        Long userId = user.getId();
        SecurityTokenPairDto tokenPair = switch (userRole) {
            case ROLE_COMPANY -> jwtService.generateSecurityTokenPairForCompany(userEmail, userId, companyId);
            case ROLE_PLAYER ->
                    jwtService.generateSecurityTokenPairForPlayer(userEmail, userId, companyId, user.getPlayer().getId());
        };
        return generateResponseWithAccessAndRefreshTokens(tokenPair, userRole);
    }

    private ResponseEntity<AuthResponseDto> generateResponseWithAccessAndRefreshTokens(SecurityTokenPairDto tokenPair, UserRole userRole) {
        Cookie refreshCookie = createRefreshTokenCookie(tokenPair.getRefreshToken());
        refreshCookie.setMaxAge(jwtService.getRefreshTokenLifetimeInSeconds());
        response.addCookie(refreshCookie);
        return ResponseEntity.ok(AuthResponseDto.builder()
                .accessToken(tokenPair.getAccessToken())
                .userRole(userRole)
                .build());
    }

    @Override
    public void logout() {
        removeRefreshTokenCookie();
    }

    private void removeRefreshTokenCookie() {
        Cookie refreshCookie = createRefreshTokenCookie("");
        refreshCookie.setMaxAge(0);
        response.addCookie(refreshCookie);
    }

    private Cookie createRefreshTokenCookie(String cookieValue) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, cookieValue);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}
