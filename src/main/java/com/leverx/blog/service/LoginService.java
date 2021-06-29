package com.leverx.blog.service;

import com.leverx.blog.dto.LoginDto;

public interface LoginService {
    void authorize(LoginDto loginDto);
}
