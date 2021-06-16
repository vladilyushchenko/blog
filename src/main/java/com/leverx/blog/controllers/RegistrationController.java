package com.leverx.blog.controllers;

import com.leverx.blog.auth.registration.RegistrationForm;
import com.leverx.blog.auth.registration.RegistrationService;
import com.leverx.blog.auth.registration.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(value = "/register")
    public HttpStatus register(@RequestBody RegistrationForm form) {
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            registrationService.register(form);
        } catch (UserAlreadyExistsException | MessagingException e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return status;
    }

    @PostMapping("/register/confirm/{hash}")
    public String confirm(@PathVariable("hash") int hash) {
        registrationService.confirmAndCreate(hash);
        return "Fine:)";
    }
}
