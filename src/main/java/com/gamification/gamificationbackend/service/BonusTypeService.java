package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.request.BonusTypeRequestDto;
import com.gamification.gamificationbackend.dto.response.BonusTypeResponseDto;

import java.util.List;

public interface BonusTypeService {

    List<BonusTypeResponseDto> getCompanyBonusTypes();
    void createBonusType(BonusTypeRequestDto bonusTypeRequestDto);
}
