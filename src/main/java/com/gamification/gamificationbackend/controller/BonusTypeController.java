package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.request.BonusTypeRequestDto;
import com.gamification.gamificationbackend.dto.response.BonusTypeResponseDto;
import com.gamification.gamificationbackend.service.BonusTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bonus-types")
@RestController
@RequiredArgsConstructor
public class BonusTypeController {

    private final BonusTypeService bonusTypeService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<BonusTypeResponseDto>> getCompanyBonusTypes() {
        return ResponseEntity.ok(bonusTypeService.getCompanyBonusTypes());
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping
    public ResponseEntity<Void> createBonusType(@RequestBody BonusTypeRequestDto bonusTypeRequestDto) {
        bonusTypeService.createBonusType(bonusTypeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
