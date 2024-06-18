package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.response.PlayerResponseDto;
import com.gamification.gamificationbackend.entity.Bonus;
import com.gamification.gamificationbackend.entity.Player;
import com.gamification.gamificationbackend.entity.ShopItem;

import java.util.List;

public interface PlayerService {

    List<PlayerResponseDto> getCompanyPlayers();

    Player rewardPlayerAndGet(Long playerId, Integer points, Integer coins, List<Bonus> bonuses);

    void takeCoinsFromPlayerForPurchase(Long playerId, ShopItem shopItem);

    void updateLevelsOfAllCompanyPlayers(Long companyId, Integer levelFormulaConstant, Integer levelFormulaPower);
}
