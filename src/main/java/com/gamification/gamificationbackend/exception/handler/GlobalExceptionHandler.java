package com.gamification.gamificationbackend.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.gamificationbackend.dto.response.ErrorResponseDto;
import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    private static final String DEFAULT_CLIENT_ERROR_MESSAGE = "Произошла непредвиденная ошибка при работе с сервером";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) throws JsonProcessingException {
        e.printStackTrace();

        HttpStatus status;
        String displayedMessage = DEFAULT_CLIENT_ERROR_MESSAGE;
        if (e instanceof GlobalException ge) {
            status = ge.getStatus();
            if (ErrorType.CLIENT.equals(ge.getType())) {
                displayedMessage = ge.getMessage();
            }
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .displayedMessage(displayedMessage)
                .message(e.getMessage())
                .build();

        log.error(objectMapper.writeValueAsString(errorResponseDto));
        return new ResponseEntity<>(errorResponseDto, status);
    }
}
