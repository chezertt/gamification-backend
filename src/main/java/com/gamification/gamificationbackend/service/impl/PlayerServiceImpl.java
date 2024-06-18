package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.response.PlayerResponseDto;
import com.gamification.gamificationbackend.entity.Bonus;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.entity.Player;
import com.gamification.gamificationbackend.entity.ShopItem;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.exception.BadRequestException;
import com.gamification.gamificationbackend.mapper.impl.PlayerMapper;
import com.gamification.gamificationbackend.repository.PlayerRepository;
import com.gamification.gamificationbackend.service.JwtService;
import com.gamification.gamificationbackend.service.PlayerService;
import com.gamification.gamificationbackend.util.LevelUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    private final JwtService jwtService;

    @PreAuthorize("isAuthenticated()")
    @Override
    public List<PlayerResponseDto> getCompanyPlayers() {
        Long companyId = jwtService.getCompanyId();
        return playerMapper.toDtos(playerRepository.findAllByUserCompanyIdOrderByPointsDesc(companyId));
    }

    @Transactional
    @Override
    public Player rewardPlayerAndGet(Long playerId, Integer points, Integer coins, List<Bonus> bonuses) {
        Player player = getPlayerById(playerId);
        if (points != null && points > 0) {
            int newPointAmount = player.getPoints() + points;
            player.setPoints(newPointAmount);
            Company company = player.getUser().getCompany();
            updatePlayerLevelAndCurrentNextLevelPoints(player, newPointAmount, company.getLevelFormulaConstant(), company.getLevelFormulaPower());
        }
        if (coins != null && coins > 0) {
            player.setCoins(player.getCoins() + coins);
        }
        if (!CollectionUtils.isEmpty(bonuses)) {
            player.getObtainedBonuses().addAll(bonuses);
        }
        return playerRepository.saveAndFlush(player);
    }

    @Transactional
    @Override
    public void updateLevelsOfAllCompanyPlayers(Long companyId, Integer levelFormulaConstant, Integer levelFormulaPower) {
        List<Player> companyPlayers = playerRepository.findAllByUserCompanyId(companyId);
        for (Player companyPlayer : companyPlayers) {
            updatePlayerLevelAndCurrentNextLevelPoints(companyPlayer, companyPlayer.getPoints(), levelFormulaConstant, levelFormulaPower);
            playerRepository.save(companyPlayer);
        }
    }

    private void updatePlayerLevelAndCurrentNextLevelPoints(Player player, Integer points, Integer levelFormulaConstant, Integer levelFormulaPower) {
        int newLevel = LevelUtil.calculateLevel(points, levelFormulaPower, levelFormulaConstant);
        int newCurrentLevelPoints = LevelUtil.calculateLevelPoints(newLevel, levelFormulaPower, levelFormulaConstant);
        int newNextLevelPoints = LevelUtil.calculateLevelPoints(newLevel + 1, levelFormulaPower, levelFormulaConstant);
        player.setLevel(newLevel);
        player.setCurrentLevelPoints(newCurrentLevelPoints);
        player.setNextLevelPoints(newNextLevelPoints);
    }

    @Transactional
    @Override
    public void takeCoinsFromPlayerForPurchase(Long playerId, ShopItem shopItem) {
        Player player = getPlayerById(playerId);
        Integer playerCoins = player.getCoins();
        Integer coinsToTake = shopItem.getPriceInCoins();
        if (playerCoins < coinsToTake) {
            throw new BadRequestException(ErrorType.CLIENT, "У вас недостаточно монет на балансе");
        } else {
            player.setCoins(playerCoins - coinsToTake);
            player.getObtainedBonuses().add(shopItem.getBonus());
            playerRepository.saveAndFlush(player);
        }
    }

    private Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new BadRequestException(ErrorType.TECH, String.format("Нет игрока с id = %s", playerId)));
    }
}
