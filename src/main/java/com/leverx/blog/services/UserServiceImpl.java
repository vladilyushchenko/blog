package com.leverx.blog.services;

import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.entities.User;
import com.leverx.blog.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(int id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("No user with id %d", id));
        }
        return user.get();
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("No user with email %s", email));
        }
        return user.get();
    }
}
