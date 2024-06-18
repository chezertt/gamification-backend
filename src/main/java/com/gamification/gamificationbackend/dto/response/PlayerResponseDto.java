package com.gamification.gamificationbackend.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String title;

    private Integer level;

    private Integer points;

    private Integer currentLevelPoints;

    private Integer nextLevelPoints;

    private Integer coins;

    private List<EventResponseDto> completedEvents;

    private List<BonusResponseDto> obtainedBonuses;
} 