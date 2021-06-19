package com.leverx.blog.services;

import com.leverx.blog.dao.UserDao;
import com.leverx.blog.entities.User;
import com.leverx.blog.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findById(int id) throws NotFoundException {
        Optional<User> user = userDao.findById(id);
        throwIfNotFound(user, String.format("No user with id %d", id));
        return user.get();
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        Optional<User> user = userDao.findByEmail(email);
        throwIfNotFound(user, String.format("No user with email %s", email));
        return user.get();
    }

    @Override
    public void persist(User user) {
        userDao.persist(user);
    }

    private void throwIfNotFound(Optional<User> user, String text) throws NotFoundException {
        if (user.isEmpty()) {
            throw new NotFoundException(text);
        }
    }
}
