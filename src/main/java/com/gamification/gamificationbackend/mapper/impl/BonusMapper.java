package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.BonusResponseDto;
import com.gamification.gamificationbackend.entity.Bonus;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import com.gamification.gamificationbackend.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DateTimeUtil.class)
public interface BonusMapper extends EntityToDtoMapper<Bonus, BonusResponseDto> {

    @Override
    @Mapping(source = "type.name", target = "type")
    BonusResponseDto toDto(Bonus entity);
}
