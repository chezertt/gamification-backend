package com.gamification.gamificationbackend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonusResponseDto {
    
    private Long id;

    private String name;

    private String type;

    private String createdAt;
}