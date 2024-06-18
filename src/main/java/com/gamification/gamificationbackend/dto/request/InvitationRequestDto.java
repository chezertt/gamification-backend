package com.gamification.gamificationbackend.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationRequestDto {

    private String playerEmail;

    private String playerFirstName;

    private String playerLastName;

    private String playerTitle;
}