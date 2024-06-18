package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.BonusTypeResponseDto;
import com.gamification.gamificationbackend.entity.BonusType;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import com.gamification.gamificationbackend.util.DateTimeUtil;
import org.mapstruct.Mapper;

@Mapper(uses = DateTimeUtil.class)
public interface BonusTypeMapper extends EntityToDtoMapper<BonusType, BonusTypeResponseDto> {

}
