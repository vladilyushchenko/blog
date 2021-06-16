package com.leverx.blog.dao;

import com.leverx.blog.model.User;
import java.util.List;

public interface UserDao {
    User getById(int id);

    List<User> get();

    User getByEmail(String email);

    void create(User user);
}
