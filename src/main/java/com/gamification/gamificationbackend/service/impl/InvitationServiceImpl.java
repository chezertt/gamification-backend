package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.dto.request.InvitationRequestDto;
import com.gamification.gamificationbackend.dto.response.CompanyResponseDto;
import com.gamification.gamificationbackend.dto.response.InvitationResponseDto;
import com.gamification.gamificationbackend.entity.Company;
import com.gamification.gamificationbackend.entity.Invitation;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.exception.BadRequestException;
import com.gamification.gamificationbackend.mapper.impl.InvitationMapper;
import com.gamification.gamificationbackend.repository.InvitationRepository;
import com.gamification.gamificationbackend.service.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;
    private final JwtService jwtService;
    private final EntityManager entityManager;
    private final LetterService letterService;
    private final MailService mailService;
    private final UserService userService;

    @Override
    public List<InvitationResponseDto> getInvitations() {
        Long companyId = jwtService.getCompanyId();
        return invitationMapper.toDtos(invitationRepository.findAllByCompanyIdOrderBySentAtDesc(companyId));
    }

    @Transactional
    @Override
    public void sendInvitation(InvitationRequestDto invitationRequestDto) {
        CompanyResponseDto company = userService.getCompanyUser().getCompany();
        String playerEmail = invitationRequestDto.getPlayerEmail();
        if (invitationRepository.existsByPlayerEmailIgnoreCaseAndCompanyId(playerEmail, company.getId())) {
            throw new BadRequestException(ErrorType.CLIENT, "Вы уже отправили приглашение игроку на почту " + playerEmail);
        }
        log.info(String.format("Отправили приглашение сотруднику на почту %s", playerEmail));
        String invitationToken = jwtService.generateInvitationToken(playerEmail, invitationRequestDto, company.getId());
        String invitationLetter = letterService.generateInvitationLetter(invitationRequestDto, invitationToken, company.getName());
        mailService.sendMail(playerEmail, "Приглашение в систему геймификации", invitationLetter);
        Invitation newInvitation = Invitation.builder()
                .company(entityManager.getReference(Company.class, company.getId()))
                .playerEmail(playerEmail)
                .playerFirstName(invitationRequestDto.getPlayerFirstName())
                .playerLastName(invitationRequestDto.getPlayerLastName())
                .playerTitle(invitationRequestDto.getPlayerTitle())
                .build();
        invitationRepository.save(newInvitation);
    }
}
