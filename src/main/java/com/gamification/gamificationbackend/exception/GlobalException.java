package com.gamification.gamificationbackend.exception;

import com.gamification.gamificationbackend.enumeration.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * All custom exception should extend this abstract class
 */
@Getter
public abstract class GlobalException extends RuntimeException {

    private final HttpStatus status;
    private final ErrorType type;
    private final String message;

    protected GlobalException(HttpStatus status, ErrorType type, String message) {
        super();
        this.status = status;
        this.type = type;
        this.message = message;
    }
}
