package com.gamification.gamificationbackend.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopItemRequestDto {

    private Long bonusId;

    private Integer priceInCoins;
} 