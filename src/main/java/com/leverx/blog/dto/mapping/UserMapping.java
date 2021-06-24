package com.leverx.blog.dto.mapping;

import com.leverx.blog.dto.UserDto;
import com.leverx.blog.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapping {
    public static User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setRoles(userDto.getRoles());
        return user;
    }

    public static UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }
}
