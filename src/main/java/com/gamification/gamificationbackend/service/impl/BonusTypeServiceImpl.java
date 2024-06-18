package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.request.BonusTypeRequestDto;
import com.gamification.gamificationbackend.dto.response.BonusTypeResponseDto;
import com.gamification.gamificationbackend.entity.BonusType;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.exception.BadRequestException;
import com.gamification.gamificationbackend.mapper.impl.BonusTypeMapper;
import com.gamification.gamificationbackend.repository.BonusTypeRepository;
import com.gamification.gamificationbackend.service.BonusTypeService;
import com.gamification.gamificationbackend.service.JwtService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BonusTypeServiceImpl implements BonusTypeService {

    private final BonusTypeRepository bonusTypeRepository;
    private final BonusTypeMapper bonusTypeMapper;
    private final JwtService jwtService;
    private final EntityManager entityManager;

    @Override
    public List<BonusTypeResponseDto> getCompanyBonusTypes() {
        Long companyId = jwtService.getCompanyId();
        return bonusTypeMapper.toDtos(bonusTypeRepository.findAllByCompanyIdOrderByCreatedAtDesc(companyId));
    }

    @Transactional
    @Override
    public void createBonusType(BonusTypeRequestDto bonusTypeRequestDto) {
        Long companyId = jwtService.getCompanyId();
        if (bonusTypeRepository.existsByNameIgnoreCaseAndCompanyId(bonusTypeRequestDto.getName(), companyId)) {
            throw new BadRequestException(ErrorType.CLIENT, "У вас уже есть созданный тип бонусов с таким названием, название типа бонусов должно быть уникальным");
        }
        BonusType newBonusType = BonusType.builder()
                .company(entityManager.getReference(Company.class, companyId))
                .name(bonusTypeRequestDto.getName())
                .build();
        bonusTypeRepository.save(newBonusType);
    }
}
