package com.leverx.blog.controllers;

import com.leverx.blog.dto.PasswordResetDto;
import com.leverx.blog.dto.UserDto;
import com.leverx.blog.services.authorization.PasswordService;
import com.leverx.blog.services.authorization.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final RegistrationService registrationService;
    private final PasswordService passwordService;

    @Autowired
    public AuthenticationController(RegistrationService registrationService, PasswordService passwordService) {
        this.registrationService = registrationService;
        this.passwordService = passwordService;
    }

    @PostMapping
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto form) {
        registrationService.register(form);
        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    @GetMapping("/confirm/{hash}")
    public void confirm(@PathVariable int hash) {
        registrationService.confirmAndCreate(hash);
    }

    @PostMapping("/forgot_password")
    public void forgotPassword(@RequestParam String email) {
        passwordService.reset(email);
    }

    @PostMapping("/reset")
    public void forgotPassword(@Valid @RequestBody PasswordResetDto resetDto) {
        passwordService.confirmReset(resetDto);
    }
}
