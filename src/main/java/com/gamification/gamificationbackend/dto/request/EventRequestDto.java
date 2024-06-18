package com.gamification.gamificationbackend.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDto {

    private String name;

    private Long typeId;

    private String description;

    private Integer points;

    private Integer coins;

    private List<Long> bonusIds;
}