package com.gamification.gamificationbackend.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDto {

    private Long id;

    private String name;

    private String type;

    private String description;

    private Integer points;

    private Integer coins;

    private List<BonusResponseDto> bonuses;

    private Boolean isAvailable;

    private String createdAt;
}