package com.gamification.gamificationbackend.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtClaim {
    USER_ID("user_id"),
    COMPANY_ID("company_id"),
    PLAYER_ID("player_id"),
    ROLE_NAME("role_name"),
    PLAYER_FIRST_NAME("player_first_name"),
    PLAYER_LAST_NAME("player_last_name"),
    PLAYER_TITLE("player_title");

    private final String value;
}
