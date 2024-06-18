package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.config.TemplateConfig;
import com.gamification.gamificationbackend.dto.request.InvitationRequestDto;
import com.gamification.gamificationbackend.service.LetterService;
import com.gamification.gamificationbackend.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LetterServiceImpl implements LetterService {

    private final TemplateService templateService;

    private final TemplateConfig templateConfig;

    @Override
    public String generateInvitationLetter(InvitationRequestDto invitationRequestDto, String invitationToken, String companyName) {
        String playerRegistrationLinkWithToken = templateConfig.getPlayerRegistrationLink() + "?invitationToken=" + invitationToken;
        return templateService.processTemplate(templateConfig.getInvitationLetterTemplatePath(), Map.of(
                "greeting", "Здравствуйте, " + invitationRequestDto.getPlayerFirstName() + " " + invitationRequestDto.getPlayerLastName() + "!",
                "topText", "Компания \"" + companyName +
                        "\" приглашает Вас в систему геймификации в качестве игрока с должностью \"" + invitationRequestDto.getPlayerTitle() + "\".",
                "link", playerRegistrationLinkWithToken
        ));
    }
}
