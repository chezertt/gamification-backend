package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.request.LevelFormulaRequestDto;
import com.gamification.gamificationbackend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PreAuthorize("hasRole('COMPANY')")
    @PatchMapping("/company/level-formula")
    public ResponseEntity<Void> updateLevelFormula(@RequestBody LevelFormulaRequestDto levelFormulaRequestDto) {
        companyService.updateLevelFormula(levelFormulaRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
