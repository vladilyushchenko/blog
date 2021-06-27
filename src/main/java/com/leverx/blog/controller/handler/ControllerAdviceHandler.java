package com.leverx.blog.controller.handler;

import com.leverx.blog.exception.NotFoundEntityException;
import com.leverx.blog.exception.UserAlreadyExistsException;
import com.leverx.blog.exception.UserNotActivatedException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.mail.MessagingException;

@ControllerAdvice
public class ControllerAdviceHandler {
    @ExceptionHandler(value = AccessDeniedException.class)
    protected @ResponseBody ResponseEntity<String> handleAccessDeniedException(
            RuntimeException exc, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exc.getMessage());
    }

    @ExceptionHandler(value = UserNotActivatedException.class)
    protected @ResponseBody ResponseEntity<String> handleUserNotActivatedException(
            RuntimeException exc, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exc.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected @ResponseBody ResponseEntity<String> handleConstraintException(
            RuntimeException exc, WebRequest request) {
        return ResponseEntity.badRequest().body(request.getDescription(true));
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    protected @ResponseBody ResponseEntity<String> handleUserAlreadyExistsException(
            RuntimeException exc, WebRequest request) {
        return ResponseEntity.badRequest().body(exc.getMessage());
    }

    @ExceptionHandler(value = MessagingException.class)
    protected @ResponseBody ResponseEntity<String> handleMessagingException(
            RuntimeException exc, WebRequest request) {
        return ResponseEntity.badRequest().body(exc.getMessage());
    }

    @ExceptionHandler(value = NotFoundEntityException.class)
    protected @ResponseBody ResponseEntity<String> handleUserNotFoundException(
            RuntimeException exc, WebRequest request) {
        return ResponseEntity.notFound().build();
    }
}
