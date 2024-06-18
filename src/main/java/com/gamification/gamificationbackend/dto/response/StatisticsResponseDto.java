package com.gamification.gamificationbackend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsResponseDto {
    
    private Long id;

    private Integer completedEvents;

    private Integer obtainedBonuses;

    private String createdAt;
}