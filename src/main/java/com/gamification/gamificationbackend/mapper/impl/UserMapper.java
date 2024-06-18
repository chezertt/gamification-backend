package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.UserResponseDto;
import com.gamification.gamificationbackend.entity.User;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import com.gamification.gamificationbackend.util.DateTimeUtil;
import org.mapstruct.Mapper;

@Mapper(uses = {CompanyMapper.class, DateTimeUtil.class})
public interface UserMapper extends EntityToDtoMapper<User, UserResponseDto> {
}
