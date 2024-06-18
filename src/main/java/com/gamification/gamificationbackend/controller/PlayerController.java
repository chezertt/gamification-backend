package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.response.PlayerResponseDto;
import com.gamification.gamificationbackend.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<PlayerResponseDto>> getPlayers() {
        return ResponseEntity.ok(playerService.getCompanyPlayers());
    }
}
