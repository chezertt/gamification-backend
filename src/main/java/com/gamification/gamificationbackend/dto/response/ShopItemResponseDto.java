package com.gamification.gamificationbackend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopItemResponseDto {

    private Long id;

    private BonusResponseDto bonus;

    private Integer priceInCoins;

    private Boolean isAvailable;

    private String createdAt;
}