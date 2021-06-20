package com.leverx.blog.controllers;

import com.leverx.blog.dto.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
//    @GetMapping("/login")
//    public HttpStatus login(@RequestBody LoginDto loginDto) {
//        return HttpStatus.ACCEPTED;
//    }

    @GetMapping("/fail")
    public HttpStatus failLogin() {
        return HttpStatus.BAD_REQUEST;
    }
}
