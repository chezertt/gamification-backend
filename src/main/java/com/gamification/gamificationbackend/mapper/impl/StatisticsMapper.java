package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.StatisticsResponseDto;
import com.gamification.gamificationbackend.entity.Statistics;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import com.gamification.gamificationbackend.util.DateTimeUtil;
import org.mapstruct.Mapper;

@Mapper(uses = DateTimeUtil.class)
public interface StatisticsMapper extends EntityToDtoMapper<Statistics, StatisticsResponseDto> {
}
