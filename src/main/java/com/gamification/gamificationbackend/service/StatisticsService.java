package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.response.StatisticsResponseDto;

import java.util.List;

public interface StatisticsService {

    List<StatisticsResponseDto> getStatisticsForLast7Days();
}
