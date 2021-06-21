package com.leverx.blog.dao;

import com.leverx.blog.entities.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    Optional<Integer> getIdByEmail(String email);

    void persist(User user);

    int save(User user);

    void updateActivatedById(int id);

    void updatePasswordByEmail(String email, String password);
}
