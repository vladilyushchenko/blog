package com.leverx.blog.service;

import com.leverx.blog.dto.UserDto;

public interface UserService {
    UserDto findByEmail(String email);

    int findIdByEmail(String email);

    UserDto save(UserDto userDto);

    boolean existsByEmail(String email);

    void setActivatedById(int id, boolean activated);
}
