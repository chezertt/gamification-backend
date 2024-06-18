package com.gamification.gamificationbackend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private String displayedMessage;

    private String message;
} 
