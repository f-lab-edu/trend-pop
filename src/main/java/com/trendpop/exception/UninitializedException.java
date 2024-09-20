package com.trendpop.exception;

import org.springframework.http.HttpStatus;

public class UninitializedException extends BusinessException {
    public UninitializedException(String resourceName) {
        super(resourceName + "_UNINITIALIZED", HttpStatus.BAD_REQUEST);
    }
}
