package com.leverx.blog.controller.controlleradvice;

import com.leverx.blog.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class UserExceptionControllerAdvice {

    @ExceptionHandler(value = NotFoundException.class)
    protected @ResponseBody
    ResponseEntity<String> handleUserAlreadyExistsException(
            RuntimeException exc, WebRequest request) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}
