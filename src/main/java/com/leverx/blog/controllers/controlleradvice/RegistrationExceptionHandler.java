package com.leverx.blog.controllers.controlleradvice;

import com.leverx.blog.exceptions.UserAlreadyExistsException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;

@ControllerAdvice
public class RegistrationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String CONSTRAINT_MESSAGE = "User with this data already exists:( " +
            "Try another email!";

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    protected @ResponseBody ResponseEntity<String> handleUserAlreadyExistsException(
            RuntimeException exc, WebRequest request) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MessagingException.class)
    protected @ResponseBody ResponseEntity<String> handleMessagingException(
            RuntimeException exc, WebRequest request) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected @ResponseBody ResponseEntity<String> handleConstraintException(
            RuntimeException exc, WebRequest request) {
        return new ResponseEntity<>(CONSTRAINT_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
