package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.InvitationResponseDto;
import com.gamification.gamificationbackend.entity.Invitation;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import com.gamification.gamificationbackend.util.DateTimeUtil;
import org.mapstruct.Mapper;

@Mapper(uses = DateTimeUtil.class)
public interface InvitationMapper extends EntityToDtoMapper<Invitation, InvitationResponseDto> {
}
