package com.gamification.gamificationbackend.exception;

import com.gamification.gamificationbackend.enumeration.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for HTTP 400 Bad Request.
 * Indicates client error.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends GlobalException {

    public BadRequestException(ErrorType type, String message) {
        super(HttpStatus.BAD_REQUEST, type, message);
    }
}
