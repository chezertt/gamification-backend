package com.gamification.gamificationbackend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationResponseDto {

    private Long id;

    private String playerEmail;

    private String playerFirstName;

    private String playerLastName;

    private String playerTitle;

    private String sentAt;
} 