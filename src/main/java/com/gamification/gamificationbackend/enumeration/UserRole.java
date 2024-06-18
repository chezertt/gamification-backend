package com.gamification.gamificationbackend.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_COMPANY,
    ROLE_PLAYER;

    @Override
    public String getAuthority() {
        return name();
    }
}
