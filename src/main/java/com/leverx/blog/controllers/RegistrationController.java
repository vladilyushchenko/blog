package com.leverx.blog.controllers;

import com.leverx.blog.dto.UserDto;
import com.leverx.blog.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto form) {
        registrationService.register(form);
        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    @GetMapping("/register/confirm/{hash}")
    public void confirm(@PathVariable("hash") int hash) {
        registrationService.confirmAndCreate(hash);
    }
}
