package com.gamification.gamificationbackend.dto.system;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityTokenPairDto {

    private String accessToken;
    
    private String refreshToken;
} 
