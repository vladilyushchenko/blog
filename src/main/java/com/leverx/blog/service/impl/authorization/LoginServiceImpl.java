package com.leverx.blog.service.impl.authorization;

import com.leverx.blog.dto.LoginDto;
import com.leverx.blog.dto.UserDto;
import com.leverx.blog.exception.NotFoundEntityException;
import com.leverx.blog.service.LoginService;
import com.leverx.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void authorize(LoginDto loginDto) {
        UserDto userDto = userService.findByEmail(loginDto.getEmail());
        if (!passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {
            throw new NotFoundEntityException("Incorrect credentials!");
        }
    }
}
