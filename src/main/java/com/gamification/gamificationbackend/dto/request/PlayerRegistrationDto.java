package com.gamification.gamificationbackend.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRegistrationDto {

    private String password;

    private String invitationToken;
} 