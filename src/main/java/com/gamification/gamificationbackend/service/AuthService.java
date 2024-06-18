package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.request.AuthRequestDto;
import com.gamification.gamificationbackend.dto.request.CompanyRegistrationDto;
import com.gamification.gamificationbackend.dto.request.PlayerRegistrationDto;
import com.gamification.gamificationbackend.dto.response.AuthResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<AuthResponseDto> login(AuthRequestDto authRequestDto);

    ResponseEntity<AuthResponseDto> registerCompany(CompanyRegistrationDto companyRegistrationDto);

    ResponseEntity<AuthResponseDto> registerPlayer(PlayerRegistrationDto playerRegistrationDto);

    ResponseEntity<AuthResponseDto> refresh();

    void logout();
}
