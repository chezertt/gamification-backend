package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.request.InvitationRequestDto;
import com.gamification.gamificationbackend.dto.system.SecurityTokenPairDto;
import com.gamification.gamificationbackend.entity.Player;
import com.gamification.gamificationbackend.enumeration.JwtClaim;
import com.gamification.gamificationbackend.enumeration.JwtTokenType;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {

    String validateTokenAndExtractEmail(String token, JwtTokenType type);

    SecurityTokenPairDto generateSecurityTokenPairForCompany(String email, Long userId, Long companyId);

    SecurityTokenPairDto generateSecurityTokenPairForPlayer(String email, Long userId, Long companyId, Long playerId);

    String generateInvitationToken(String email, InvitationRequestDto invitationRequestDto, Long companyId);
    Player validateInvitationTokenAndExtractPlayerUserData(String invitationToken);

    int getRefreshTokenLifetimeInSeconds();

    String extractAccessTokenFromRequest(HttpServletRequest request);

    <T> T extractClaimValueFromAccessToken(JwtClaim claim, Class<T> claimClass);

    Long getCompanyId();

    Long getPlayerId();
}
