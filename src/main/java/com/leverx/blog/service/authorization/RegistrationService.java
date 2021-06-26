package com.leverx.blog.service.authorization;

import com.leverx.blog.dto.UserDto;
import com.leverx.blog.dto.mapping.UserMapping;
import com.leverx.blog.entity.Role;
import com.leverx.blog.entity.User;
import com.leverx.blog.exception.IncorrectEmailDataException;
import com.leverx.blog.exception.UserAlreadyExistsException;
import com.leverx.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RegistrationService {
    private static final String CONFIRM_MESSAGE_WITHOUT_HASH =
            "Hello! Now you should confirm yourself via this link: " +
                    "http://localhost:8080/auth/confirm/";
    private static final String CONFIRM_TOPIC = "News agency registration";

    private final UserRepository userRepository;
    private final MailSenderService mailSender;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public RegistrationService(UserRepository userRepository, MailSenderService mailSender,
                               PasswordEncoder passwordEncoder, RedisTemplate<Object, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    public void register(UserDto userDto) {
        Optional<User> dbUser = userRepository.findUserByEmail(userDto.getEmail());
        if (dbUser.isPresent() && dbUser.get().isActivated()) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        User user;
        if (dbUser.isEmpty()) {
            user = UserMapping.mapToEntity(userDto);
            user.setCreatedAt(new Date());
            user.setPassword(cryptPassword(userDto.getPassword()));
            user.setRoles(Collections.singleton(Role.USER_ROLE));
            user = userRepository.save(user);
        } else {
            user = dbUser.get();
        }

        int hash = user.hashCode();
        redisTemplate.opsForValue().set(hash, user.getId(), 24, TimeUnit.HOURS);
        sendConfirmMessage(userDto.getEmail(), hash);
    }

    public void confirmAndCreate(int hash) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(hash))) {
            int userId = Integer.parseInt(String.valueOf(redisTemplate.opsForValue().get(hash)));
            userRepository.setActivatedById(userId, true);
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

}
