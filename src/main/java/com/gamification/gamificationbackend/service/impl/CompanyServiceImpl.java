package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.request.LevelFormulaRequestDto;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.exception.InternalServerErrorException;
import com.gamification.gamificationbackend.repository.CompanyRepository;
import com.gamification.gamificationbackend.service.CompanyService;
import com.gamification.gamificationbackend.service.JwtService;
import com.gamification.gamificationbackend.service.PlayerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final JwtService jwtService;

    private final CompanyRepository companyRepository;

    private final PlayerService playerService;

    @Transactional
    @Override
    public void updateLevelFormula(LevelFormulaRequestDto levelFormulaRequestDto) {
        Long companyId = jwtService.getCompanyId();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new InternalServerErrorException(ErrorType.TECH, "Нет компании с id = " + companyId));
        boolean isFormulaUpdated = false;

        Integer newLevelFormulaConstant = levelFormulaRequestDto.getLevelFormulaConstant();
        if (newLevelFormulaConstant != null) {
            company.setLevelFormulaConstant(newLevelFormulaConstant);
            isFormulaUpdated = true;
        }

        Integer levelFormulaPower = levelFormulaRequestDto.getLevelFormulaPower();
        if (levelFormulaPower != null) {
            company.setLevelFormulaPower(levelFormulaPower);
            isFormulaUpdated = true;
        }

        if (isFormulaUpdated) {
            company = companyRepository.saveAndFlush(company);
            playerService.updateLevelsOfAllCompanyPlayers(companyId, company.getLevelFormulaConstant(), company.getLevelFormulaPower());
        }
    }
}
