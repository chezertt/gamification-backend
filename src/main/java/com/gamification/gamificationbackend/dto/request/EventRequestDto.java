package com.gamification.gamificationbackend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDto {

    private @NotEmpty String name;

    private Long typeId;

    private String description;

    private Integer points;

    private Integer coins;

    private List<Long> bonusIds;
}