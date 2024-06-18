package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.request.InvitationRequestDto;
import com.gamification.gamificationbackend.dto.response.InvitationResponseDto;
import com.gamification.gamificationbackend.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PreAuthorize("hasRole('COMPANY')")
    @GetMapping
    public ResponseEntity<List<InvitationResponseDto>> getInvitations() {
        return ResponseEntity.ok(invitationService.getInvitations());
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping
    public ResponseEntity<Void> sendInvitation(@RequestBody InvitationRequestDto invitationRequestDto) {
        invitationService.sendInvitation(invitationRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
