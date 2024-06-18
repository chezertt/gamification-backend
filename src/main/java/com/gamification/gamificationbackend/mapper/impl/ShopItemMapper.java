package com.gamification.gamificationbackend.mapper.impl;

import com.gamification.gamificationbackend.dto.response.ShopItemResponseDto;
import com.gamification.gamificationbackend.entity.ShopItem;
import com.gamification.gamificationbackend.mapper.EntityToDtoMapper;
import com.gamification.gamificationbackend.util.DateTimeUtil;
import org.mapstruct.Mapper;

@Mapper(uses = {BonusMapper.class, DateTimeUtil.class})
public interface ShopItemMapper extends EntityToDtoMapper<ShopItem, ShopItemResponseDto> {
}
