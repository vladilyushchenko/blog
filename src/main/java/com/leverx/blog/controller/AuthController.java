package com.leverx.blog.controller;

import com.leverx.blog.dto.LoginDto;
import com.leverx.blog.dto.PasswordResetDto;
import com.leverx.blog.dto.UserDto;
import com.leverx.blog.security.jwt.JwtProvider;
import com.leverx.blog.service.LoginService;
import com.leverx.blog.service.PasswordService;
import com.leverx.blog.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;
    private final PasswordService passwordService;
    private final JwtProvider jwtProvider;
    private final LoginService loginService;

    @PostMapping
    public void register(@Valid @RequestBody UserDto form) {
        registrationService.register(form);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        loginService.authorize(loginDto);
        String token = jwtProvider.generateToken(loginDto.getEmail());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/confirm/{hash}")
    public void confirm(@PathVariable String hash) {
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
