package com.gamification.gamificationbackend.dto.response;

import com.gamification.gamificationbackend.enumeration.UserRole;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    private String accessToken;

    private UserRole userRole;
}
