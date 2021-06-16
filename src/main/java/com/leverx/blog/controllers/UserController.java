package com.leverx.blog.controllers;

import com.leverx.blog.dao.UserDao;
import com.leverx.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userDao.getById(id);
    }

    @GetMapping("/all_users")
    public List<User> getUsers() {
        List<User> list = userDao.get();
        return list;
    }
}
