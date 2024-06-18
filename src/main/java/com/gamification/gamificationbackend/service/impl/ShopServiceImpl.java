package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.request.ShopItemRequestDto;
import com.gamification.gamificationbackend.dto.response.ShopItemResponseDto;
import com.gamification.gamificationbackend.entity.Bonus;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.entity.Player;
import com.gamification.gamificationbackend.entity.ShopItem;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.exception.BadRequestException;
import com.gamification.gamificationbackend.mapper.impl.ShopItemMapper;
import com.gamification.gamificationbackend.repository.ShopItemRepository;
import com.gamification.gamificationbackend.service.JwtService;
import com.gamification.gamificationbackend.service.PlayerService;
import com.gamification.gamificationbackend.service.ShopService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopItemRepository shopItemRepository;
    private final ShopItemMapper shopItemMapper;
    private final JwtService jwtService;
    private final EntityManager entityManager;
    private final PlayerService playerService;

    @Override
    public List<ShopItemResponseDto> getShopItems() {
        Long companyId = jwtService.getCompanyId();
        return shopItemMapper.toDtos(shopItemRepository.findAllByCompanyIdOrderByCreatedAtDesc(companyId));
    }

    @Override
    public void addShopItem(ShopItemRequestDto shopItemRequestDto) {
        if (shopItemRepository.existsByBonusId(shopItemRequestDto.getBonusId())) {
            throw new BadRequestException(ErrorType.CLIENT, "Данный бонус уже выставлен на продажу в магазине");
        }
        Long companyId = jwtService.getCompanyId();
        ShopItem newShopItemForSave = ShopItem.builder()
                .company(entityManager.getReference(Company.class, companyId))
                .bonus(entityManager.getReference(Bonus.class, shopItemRequestDto.getBonusId()))
                .priceInCoins(shopItemRequestDto.getPriceInCoins())
                .build();
        shopItemRepository.save(newShopItemForSave);
    }

    @Override
    public void deleteById(Long shopItemId) {
        shopItemRepository.deleteById(shopItemId);
    }

    @Transactional
    @Override
    public void buyShopItem(Long shopItemId) {
        Long playerId = jwtService.getPlayerId();
        ShopItem shopItem = shopItemRepository.findById(shopItemId)
                .orElseThrow(() -> new BadRequestException(ErrorType.TECH, "No shop item with ID = " + shopItemId));
        Bonus shopItemBonus = shopItem.getBonus();
        if (shopItemBonus.getPlayersWhoObtainBonus().contains(entityManager.getReference(Player.class, playerId))) {
            throw new BadRequestException(ErrorType.CLIENT, "У вас уже есть данная награда");
        }

        playerService.takeCoinsFromPlayerForPurchase(playerId, shopItem);
    }
}
