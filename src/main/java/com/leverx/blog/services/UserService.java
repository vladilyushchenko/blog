package com.leverx.blog.services;

import com.leverx.blog.entities.User;

public interface UserService {
    User findById(int id);

    User findByEmail(String email);

    void create(User user);
}
