package com.gamification.gamificationbackend.exception;

import com.gamification.gamificationbackend.enumeration.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for HTTP 401 Unauthorized.
 * If request lacks valid authentication credentials.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends GlobalException {

    public UnauthorizedException(ErrorType type, String message) {
        super(HttpStatus.UNAUTHORIZED, type, message);
    }
}
