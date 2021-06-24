package com.leverx.blog.services;

import com.leverx.blog.dto.UserDto;

public interface UserService {
    UserDto findById(int id);

    UserDto findByEmail(String email);

    int findIdByEmail(String email);
}
