package com.leverx.blog.service.impl.auth;

import com.leverx.blog.dto.LoginDto;
import com.leverx.blog.dto.UserDto;
import com.leverx.blog.exception.NotFoundEntityException;
import com.leverx.blog.exception.UserNotActivatedException;
import com.leverx.blog.service.LoginService;
import com.leverx.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void authorize(LoginDto loginDto) {
        log.info(String.format("TRYING AUTHORIZE WITH EMAIL %s AND PASSWORD %s",
                loginDto.getEmail(), loginDto.getPassword()));
        UserDto userDto = userService.findByEmail(loginDto.getEmail());

        if (!passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {
            throw new NotFoundEntityException("Incorrect credentials!");
        }
        if (!userDto.isActivated()) {
            throw new UserNotActivatedException("User not activated!");
        }
    }
}