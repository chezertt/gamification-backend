package com.gamification.gamificationbackend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonusTypeResponseDto {
    
    private Long id;

    private String name;

    private String createdAt;
}