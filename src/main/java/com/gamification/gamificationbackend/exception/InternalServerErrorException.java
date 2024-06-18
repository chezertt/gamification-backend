package com.gamification.gamificationbackend.exception;

import com.gamification.gamificationbackend.enumeration.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for HTTP 500 Internal Server Error.
 * Indicates server-side issues.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends GlobalException {

    public InternalServerErrorException(ErrorType type, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, type, message);
    }
}
