package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.response.StatisticsResponseDto;
import com.gamification.gamificationbackend.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PreAuthorize("hasRole('COMPANY')")
    @GetMapping
    public ResponseEntity<List<StatisticsResponseDto>> getStatistics() {
        return ResponseEntity.ok(statisticsService.getStatisticsForLast7Days());
    }
}
