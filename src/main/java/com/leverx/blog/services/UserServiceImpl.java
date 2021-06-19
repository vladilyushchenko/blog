package com.leverx.blog.services;

import com.leverx.blog.dao.UserDao;
import com.leverx.blog.entities.User;
import com.leverx.blog.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findById(int id) throws UserNotFoundException {
        Optional<User> user = userDao.findById(id);
        throwIfNotFound(user, String.format("No user with id %d", id));
        return user.get();
    }

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userDao.findByEmail(email);
        throwIfNotFound(user, String.format("No user with email %s", email));
        return user.get();
    }

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    private void throwIfNotFound(Optional<User> user, String text) throws UserNotFoundException {
        if (user.isEmpty()) {
            throw new UserNotFoundException(text);
        }
    }
}
