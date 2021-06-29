package com.leverx.blog.service.impl.auth;

import com.leverx.blog.dto.UserDto;
import com.leverx.blog.entity.Role;
import com.leverx.blog.entity.User;
import com.leverx.blog.exception.IncorrectEmailDataException;
import com.leverx.blog.exception.NotFoundEntityException;
import com.leverx.blog.exception.UserAlreadyExistsException;
import com.leverx.blog.exception.UserNotActivatedException;
import com.leverx.blog.mapper.UserMapper;
import com.leverx.blog.redis.RedisService;
import com.leverx.blog.service.MailSenderService;
import com.leverx.blog.service.RegistrationService;
import com.leverx.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
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
        log.info("REGISTRATION OF USER WITH EMAIL " + userDto.getEmail());
        User user = createUserIfNotExists(userDto);
        log.info("SAVING USER TO DB");
        user.setId(userService.save(userMapper.mapToDto(user)).getId());
        String uuidHash = UUID.randomUUID().toString();
        redisService.add(uuidHash, user.getId(), TIMEOUT_HOURS, TimeUnit.HOURS);
        sendConfirmMessage(userDto.getEmail(), uuidHash);
    }

    public void confirmAndCreate(String hash) {
        log.info("REGISTRATION CONFIRMATION WITH HASH " + hash);
        if (!redisService.contains(hash)) {
            throw new NotFoundEntityException(String.format("It's no password-update request with hash %s", hash));
        }
        int userId = Integer.parseInt(String.valueOf(redisService.retrieve(hash)));
        userService.setActivatedById(userId, true);
    }

    private void sendConfirmMessage(String email, String hash) {
        log.info("SENDING REGISTRATION CONFIRMATION EMAIL");
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
        throwIfUserExists(userDto);
        return createUser(userDto);
    }

    private void throwIfUserExists(UserDto userDto) {
        try {
            UserDto dbUser = userService.findByEmail(userDto.getEmail());
            if (!dbUser.isActivated()) {
                throw new UserNotActivatedException(String.format("User with email %s not activated!",
                        userDto.getEmail()));
            } else {
                throw new UserAlreadyExistsException(String.format("User with email %s already exists!",
                        userDto.getEmail()));
            }
        } catch (NotFoundEntityException ignored) {
            // if user doesnt exist at the moment in db
        }
    }

    private User createUser(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        user.setCreatedAt(new Date());
        user.setPassword(cryptPassword(userDto.getPassword()));
        user.setRoles(Collections.singleton(Role.USER_ROLE));
        return user;
    }
}
