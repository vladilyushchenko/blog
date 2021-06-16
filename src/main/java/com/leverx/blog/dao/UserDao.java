package com.leverx.blog.dao;


import com.leverx.blog.data.User;

import java.util.List;

public interface UserDao {

    User getUserById(int id);

    List<User> getUsers();
}
