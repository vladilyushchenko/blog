package com.leverx.blog.controller.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ArticleExceptionControllerAdvice {

    @ExceptionHandler(value = AccessDeniedException.class)
    protected @ResponseBody ResponseEntity<String> handleAccessDeniedException(
            RuntimeException exc, WebRequest request) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.FORBIDDEN);
    }

}