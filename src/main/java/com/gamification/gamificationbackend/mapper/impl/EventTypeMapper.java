package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.EventTypeResponseDto;
import com.gamification.gamificationbackend.entity.EventType;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import com.gamification.gamificationbackend.util.DateTimeUtil;
import org.mapstruct.Mapper;

@Mapper(uses = DateTimeUtil.class)
public interface EventTypeMapper extends EntityToDtoMapper<EventType, EventTypeResponseDto> {

}
