package com.leverx.blog.controllers;

import com.leverx.blog.dao.UserDao;
import com.leverx.blog.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userDao.getUserById(id);
    }
}
