package com.leverx.blog.service.impl.authorization;

import com.leverx.blog.dto.UserDto;
import com.leverx.blog.entity.Role;
import com.leverx.blog.entity.User;
import com.leverx.blog.exception.IncorrectEmailDataException;
import com.leverx.blog.exception.UserAlreadyExistsException;
import com.leverx.blog.mapper.UserMapper;
import com.leverx.blog.service.MailSenderService;
import com.leverx.blog.service.RedisService;
import com.leverx.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements com.leverx.blog.service.RegistrationService {
    private static final String CONFIRM_MESSAGE_WITHOUT_HASH =
            "Hello! Now you should confirm yourself via this link: " +
                    "http://localhost:8080/auth/confirm/";
    private static final String CONFIRM_TOPIC = "News agency registration";
    private static final int TIMEOUT_HOURS = 24;

    private final UserService userService;
    private final MailSenderService mailSender;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final UserMapper userMapper;


    public void register(UserDto userDto) {
        User user = createUserIfNotExists(userDto);
        userService.save(userMapper.mapToDto(user));
        int hash = user.hashCode();
        redisService.add(hash, user.getId(), TIMEOUT_HOURS, TimeUnit.HOURS);
        sendConfirmMessage(userDto.getEmail(), hash);
    }

    public void confirmAndCreate(int hash) {
        if (redisService.contains(hash)) {
            int userId = Integer.parseInt(String.valueOf(redisService.retrieve(hash)));
            userService.setActivatedById(userId, true);
        }
    }

    private void sendConfirmMessage(String email, int hash) {
        try {
            mailSender.sendEmail(email, CONFIRM_TOPIC, CONFIRM_MESSAGE_WITHOUT_HASH + hash);
        } catch (MessagingException e) {
            throw new IncorrectEmailDataException(e);
        }
    }

    private String cryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private User createUserIfNotExists(UserDto userDto) {
        if (userService.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        return createUser(userDto);
    }

    private User createUser(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        user.setCreatedAt(new Date());
        user.setPassword(cryptPassword(userDto.getPassword()));
        user.setRoles(Collections.singleton(Role.USER_ROLE));
        return user;
    }
}
