package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.request.InvitationRequestDto;

public interface LetterService {

    String generateInvitationLetter(InvitationRequestDto invitationRequestDto, String invitationToken, String companyName);
}
