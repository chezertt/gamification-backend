package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.request.BonusRequestDto;
import com.gamification.gamificationbackend.dto.response.BonusResponseDto;
import com.gamification.gamificationbackend.entity.Bonus;
import com.gamification.gamificationbackend.entity.BonusType;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.exception.BadRequestException;
import com.gamification.gamificationbackend.mapper.impl.BonusMapper;
import com.gamification.gamificationbackend.repository.BonusRepository;
import com.gamification.gamificationbackend.service.BonusService;
import com.gamification.gamificationbackend.service.JwtService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BonusServiceImpl implements BonusService {

    private final BonusRepository bonusRepository;
    private final BonusMapper bonusMapper;
    private final EntityManager entityManager;
    private final JwtService jwtService;

    @Override
    public List<BonusResponseDto> getCompanyBonuses() {
        Long companyId = jwtService.getCompanyId();
        return bonusMapper.toDtos(bonusRepository.findAllByCompanyIdOrderByCreatedAtDesc(companyId));
    }

    @Override
    public List<BonusResponseDto> getObtainedByPlayerBonuses() {
        Long playerId = jwtService.getPlayerId();
        return bonusMapper.toDtos(bonusRepository.findAllByPlayersWhoObtainBonus_IdOrderByCreatedAtDesc(playerId));
    }

    @Transactional
    @Override
    public void createBonus(BonusRequestDto bonusRequestDto) {
        Long companyId = jwtService.getCompanyId();
        if (bonusRepository.existsByNameIgnoreCaseAndCompanyId(bonusRequestDto.getName(), companyId)) {
            throw new BadRequestException(ErrorType.CLIENT, "У вас уже есть созданный бонус с таким названием, название бонуса должно быть уникальным");
        }
        Bonus newBonus = Bonus.builder()
                .company(entityManager.getReference(Company.class, companyId))
                .name(bonusRequestDto.getName())
                .build();
        Long bonusTypeId = bonusRequestDto.getTypeId();
        if (bonusTypeId != null) {
            newBonus.setType(entityManager.getReference(BonusType.class, bonusTypeId));
        }
        bonusRepository.save(newBonus);
    }

    @Transactional
    @Override
    public void deleteBonusById(Long bonusId) {
        Bonus bonus = bonusRepository.findById(bonusId)
                .orElseThrow((() -> new BadRequestException(ErrorType.TECH, String.format("Нет бонуса с id = %s", bonusId))));

        boolean hasLinkedEvents = !CollectionUtils.isEmpty(bonus.getEvents());
        if (hasLinkedEvents) {
            throw new BadRequestException(ErrorType.CLIENT, "Бонус невозможно удалить, так как есть события, у которых награда включает этот бонус");
        }

        boolean hasLinkedShopItem = bonus.getShopItem() != null;
        if (hasLinkedShopItem) {
            throw new BadRequestException(ErrorType.CLIENT, "Бонус невозможно удалить, так как он выставлен в магазине бонусов");
        }

        bonusRepository.deleteById(bonusId);
    }
}
