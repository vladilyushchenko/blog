package com.leverx.blog.controllers;

import com.leverx.blog.dao.UserDao;
import com.leverx.blog.entities.User;
import com.leverx.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;
    private final UserDao userDao;

    @Autowired
    public UserController(UserService userService, UserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id, Principal principal) {
        int stop = 0;
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }
}
