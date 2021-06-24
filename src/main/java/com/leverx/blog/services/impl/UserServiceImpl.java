package com.leverx.blog.services.impl;

import com.leverx.blog.dto.UserDto;
import com.leverx.blog.dto.mapping.UserMapping;
import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.entities.User;
import com.leverx.blog.exceptions.NotFoundException;
import com.leverx.blog.services.UserService;
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
    public UserDto findById(int id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("No user with id %d", id));
        }
        return UserMapping.mapToDto(user.get());
    }

    @Override
    public UserDto findByEmail(String email) throws NotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("No user with email %s", email));
        }
        return UserMapping.mapToDto(user.get());
    }

    @Override
    public int findIdByEmail(String email) {
        Optional<Integer> id = userRepository.findIdByEmail(email);
        if (id.isEmpty()) {
            throw new NotFoundException(String.format("No user with id %d", id));
        }
        return id.get();
    }

}
