package com.leverx.blog.dto.mapping;

import com.leverx.blog.dto.UserDto;
import com.leverx.blog.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapping {
    public static User mapToUserEntity(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
