package com.leverx.blog.controller.controlleradvice;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class DbExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected @ResponseBody ResponseEntity<String> handleConstraintException(
            RuntimeException exc, WebRequest request) {
        return ResponseEntity.badRequest().body(request.getDescription(true));
    }
}
