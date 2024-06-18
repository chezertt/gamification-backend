package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.request.CompanyRegistrationDto;
import com.gamification.gamificationbackend.dto.request.PlayerRegistrationDto;
import com.gamification.gamificationbackend.dto.response.PlayerUserResponseDto;
import com.gamification.gamificationbackend.dto.response.UserResponseDto;
import com.gamification.gamificationbackend.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User saveCompanyUserAndGet(CompanyRegistrationDto companyRegistrationDto);
    User savePlayerUserAndGet(PlayerRegistrationDto playerRegistrationDto);

    UserResponseDto getCompanyUser();

    PlayerUserResponseDto getPlayerUser();
}
