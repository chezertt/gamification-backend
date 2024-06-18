package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.request.BonusRequestDto;
import com.gamification.gamificationbackend.dto.response.BonusResponseDto;

import java.util.List;

public interface BonusService {

    List<BonusResponseDto> getCompanyBonuses();
    List<BonusResponseDto> getObtainedByPlayerBonuses();

    void createBonus(BonusRequestDto bonusRequestDto);
    void deleteBonusById(Long bonusId);
}
