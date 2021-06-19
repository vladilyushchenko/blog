package com.leverx.blog.dao;

import com.leverx.blog.entities.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    void create(User user);
}
