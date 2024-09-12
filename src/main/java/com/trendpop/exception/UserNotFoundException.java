package com.trendpop.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super("USER_NOT_FOUND", HttpStatus.UNAUTHORIZED);
    }
}
