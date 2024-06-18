package com.gamification.gamificationbackend.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRegistrationDto {

    private String name;

    private String email;

    private String password;
} 