package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.request.AuthRequestDto;
import com.gamification.gamificationbackend.dto.request.CompanyRegistrationDto;
import com.gamification.gamificationbackend.dto.request.PlayerRegistrationDto;
import com.gamification.gamificationbackend.dto.response.AuthResponseDto;
import com.gamification.gamificationbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        return authService.login(authRequestDto);
    }

    @PostMapping("/company/registration")
    public ResponseEntity<AuthResponseDto> registerCompany(@RequestBody CompanyRegistrationDto companyRegistrationDto) {
        return authService.registerCompany(companyRegistrationDto);
    }

    @PostMapping("/player/registration")
    public ResponseEntity<AuthResponseDto> registerCompany(@RequestBody PlayerRegistrationDto playerRegistrationDto) {
        return authService.registerPlayer(playerRegistrationDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh() {
        return authService.refresh();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
