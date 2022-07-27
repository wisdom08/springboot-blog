package com.wisdom.blogwithoutlogin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class WrongPasswordException extends RuntimeException {
    private final String resourceName;
    private final boolean fieldValue;

    public WrongPasswordException(String resourceName, boolean fieldValue) {
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}
