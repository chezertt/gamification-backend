package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.response.PlayerUserResponseDto;
import com.gamification.gamificationbackend.dto.response.UserResponseDto;
import com.gamification.gamificationbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('COMPANY')")
    @GetMapping("/company")
    public ResponseEntity<UserResponseDto> getCompanyUser() {
        return ResponseEntity.ok(userService.getCompanyUser());
    }

    @PreAuthorize("hasRole('PLAYER')")
    @GetMapping("/player")
    public ResponseEntity<PlayerUserResponseDto> getPlayerUser() {
        return ResponseEntity.ok(userService.getPlayerUser());
    }
}
