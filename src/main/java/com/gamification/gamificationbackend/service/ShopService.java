package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.request.ShopItemRequestDto;
import com.gamification.gamificationbackend.dto.response.ShopItemResponseDto;

import java.util.List;

public interface ShopService {

    List<ShopItemResponseDto> getShopItems();

    void addShopItem(ShopItemRequestDto shopItemRequestDto);

    void deleteById(Long shopItemId);

    void buyShopItem(Long shopItemId);
}
