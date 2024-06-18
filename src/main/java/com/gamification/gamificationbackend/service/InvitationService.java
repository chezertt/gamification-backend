package com.gamification.gamificationbackend.service;

import com.gamification.gamificationbackend.dto.request.InvitationRequestDto;
import com.gamification.gamificationbackend.dto.response.InvitationResponseDto;

import java.util.List;

public interface InvitationService {

    List<InvitationResponseDto> getInvitations();
    void sendInvitation(InvitationRequestDto invitationRequestDto);
}
