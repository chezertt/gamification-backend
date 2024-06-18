package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.request.BonusRequestDto;
import com.gamification.gamificationbackend.dto.response.BonusResponseDto;
import com.gamification.gamificationbackend.service.BonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bonuses")
@RestController
@RequiredArgsConstructor
public class BonusController {

    private final BonusService bonusService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<BonusResponseDto>> getCompanyBonuses() {
        return ResponseEntity.ok(bonusService.getCompanyBonuses());
    }

    @PreAuthorize("hasRole('PLAYER')")
    @GetMapping("/obtained")
    public ResponseEntity<List<BonusResponseDto>> getObtainedByPlayerBonuses() {
        return ResponseEntity.ok(bonusService.getObtainedByPlayerBonuses());
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping
    public ResponseEntity<Void> createBonus(@RequestBody BonusRequestDto bonusRequestDto) {
        bonusService.createBonus(bonusRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('COMPANY')")
    @DeleteMapping("/{bonusId}")
    public ResponseEntity<Void> deleteBonusById(@PathVariable Long bonusId) {
        bonusService.deleteBonusById(bonusId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
