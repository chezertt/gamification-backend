package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.request.CompanyRegistrationDto;
import com.gamification.gamificationbackend.dto.request.PlayerRegistrationDto;
import com.gamification.gamificationbackend.dto.response.PlayerUserResponseDto;
import com.gamification.gamificationbackend.dto.response.UserResponseDto;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.entity.Player;
import com.gamification.gamificationbackend.entity.User;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.enumeration.JwtClaim;
import com.gamification.gamificationbackend.enumeration.UserRole;
import com.gamification.gamificationbackend.exception.BadRequestException;
import com.gamification.gamificationbackend.exception.InternalServerErrorException;
import com.gamification.gamificationbackend.mapper.impl.PlayerUserMapper;
import com.gamification.gamificationbackend.mapper.impl.UserMapper;
import com.gamification.gamificationbackend.repository.CompanyRepository;
import com.gamification.gamificationbackend.repository.PlayerRepository;
import com.gamification.gamificationbackend.repository.UserRepository;
import com.gamification.gamificationbackend.service.JwtService;
import com.gamification.gamificationbackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    private final PlayerRepository playerRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserMapper userMapper;

    private final PlayerUserMapper playerUserMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorType.CLIENT, String.format("Нет пользователя с почтой \"%s\"", email)));
    }

    @Transactional
    @Override
    public User saveCompanyUserAndGet(CompanyRegistrationDto companyRegistrationDto) {
        String companyName = companyRegistrationDto.getName();
        if (companyRepository.existsByName(companyName)) {
            throw new BadRequestException(ErrorType.CLIENT, String.format("Компания с названием \"%s\" уже существует в системе", companyName));
        }
        String companyEmail = companyRegistrationDto.getEmail();
        if (userRepository.existsByEmail(companyEmail)) {
            throw new BadRequestException(ErrorType.CLIENT, String.format("Пользователь с почтой \"%s\" уже существует в системе", companyEmail));
        }

        Company savedCompany = companyRepository.saveAndFlush(Company.builder()
                .name(companyName)
                .build());

        return userRepository.saveAndFlush(User.builder()
                .company(savedCompany)
                .email(companyEmail)
                .password(passwordEncoder.encode(companyRegistrationDto.getPassword()))
                .role(UserRole.ROLE_COMPANY)
                .build());
    }

    @Transactional
    @Override
    public User savePlayerUserAndGet(PlayerRegistrationDto playerRegistrationDto) {
        Player player = jwtService.validateInvitationTokenAndExtractPlayerUserData(playerRegistrationDto.getInvitationToken());
        User user = player.getUser();
        user.setPassword(passwordEncoder.encode(playerRegistrationDto.getPassword()));
        user.setRole(UserRole.ROLE_PLAYER);
        user = userRepository.saveAndFlush(user);
        player.setUser(user);
        player = playerRepository.saveAndFlush(player);
        user.setPlayer(player);
        return user;
    }

    @Override
    public UserResponseDto getCompanyUser() {
        return userMapper.toDto(getUser());
    }

    @Override
    public PlayerUserResponseDto getPlayerUser() {
        return playerUserMapper.toDto(getUser());
    }

    private User getUser() {
        Long userId = jwtService.extractClaimValueFromAccessToken(JwtClaim.USER_ID, Long.class);
        UserRole userRole = UserRole.valueOf(jwtService.extractClaimValueFromAccessToken(JwtClaim.ROLE_NAME, String.class));
        return userRepository.findByIdAndRole(userId, userRole)
                .orElseThrow(() -> new InternalServerErrorException(ErrorType.TECH, String.format("Нет пользователя с id = \"%s\" и ролью = \"%s\"", userId, userRole)));
    }
}
