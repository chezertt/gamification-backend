package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.PlayerResponseDto;
import com.gamification.gamificationbackend.entity.Player;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {EventMapper.class, BonusMapper.class})
public interface PlayerMapper extends EntityToDtoMapper<Player, PlayerResponseDto> {
}
