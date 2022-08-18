package com.wisdom.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import global.Response;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<?> illegalArgumentExceptionAdvice(IllegalArgumentException e) {
        return new Response<>(200, e.getMessage(), null);
    }

}
