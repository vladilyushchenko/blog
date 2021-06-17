package com.leverx.blog.dao;

import com.leverx.blog.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User getById(int id);

    List<User> get();

    Optional<User> getByEmail(String email);

    void create(User user);
}
