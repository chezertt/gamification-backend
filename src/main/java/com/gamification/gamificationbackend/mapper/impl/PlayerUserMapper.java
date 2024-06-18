package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.PlayerUserResponseDto;
import com.gamification.gamificationbackend.entity.User;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import com.gamification.gamificationbackend.util.DateTimeUtil;
import org.mapstruct.Mapper;

@Mapper(uses = {PlayerMapper.class, DateTimeUtil.class})
public interface PlayerUserMapper extends EntityToDtoMapper<User, PlayerUserResponseDto> {
}
