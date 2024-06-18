package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.EventResponseDto;
import com.gamification.gamificationbackend.entity.Event;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import com.gamification.gamificationbackend.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {BonusMapper.class, DateTimeUtil.class})
public interface EventMapper extends EntityToDtoMapper<Event, EventResponseDto> {

    @Override
    @Mapping(source = "type.name", target = "type")
    EventResponseDto toDto(Event entity);
}
