package com.leverx.blog.service;

import com.leverx.blog.dto.UserDto;

public interface RegistrationService {
    void register(UserDto userDto);

    void confirmAndCreate(String hash);
}
