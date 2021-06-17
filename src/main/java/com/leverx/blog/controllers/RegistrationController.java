package com.leverx.blog.controllers;

import com.leverx.blog.auth.registration.RegistrationService;
import com.leverx.blog.auth.registration.UserAlreadyExistsException;
import com.leverx.blog.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(value = "/register")
    public HttpStatus register(@Valid @RequestBody UserDto form) {
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            registrationService.register(form);
        } catch (UserAlreadyExistsException | MessagingException e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return status;
    }

    @GetMapping("/register/confirm/{hash}")
    public HttpStatus confirm(@PathVariable("hash") int hash) {
        registrationService.confirmAndCreate(hash);
        return HttpStatus.CREATED;
    }
}
