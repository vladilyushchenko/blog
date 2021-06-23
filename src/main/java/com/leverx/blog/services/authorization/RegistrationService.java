package com.leverx.blog.services.authorization;

import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.dto.UserDto;
import com.leverx.blog.dto.mapping.UserMapping;
import com.leverx.blog.entities.Role;
import com.leverx.blog.entities.User;
import com.leverx.blog.exceptions.IncorrectEmailDataException;
import com.leverx.blog.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistrationService {
    private static final int ONE_HOUR_IN_MILLIS = 86400000;

    private final UserRepository userRepository;
    private final MailSenderService mailSender;
    private final Map<Integer, User> waitingForConfirm = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, MailSenderService mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserDto userDto) {
        Optional<User> dbUser = userRepository.findUserByEmail(userDto.getEmail());
        if (dbUser.isPresent() && dbUser.get().isActivated()) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        User user;
        if (dbUser.isEmpty()) {
            user = UserMapping.mapToUserEntity(userDto);
            user.setCreatedAt(new Date());
            user.setPassword(cryptPassword(userDto.getPassword()));
            user.setRoles(Collections.singleton(Role.USER_ROLE));
            userRepository.save(user);
        } else {
            user = dbUser.get();
        }

        int hash = user.hashCode();
        waitingForConfirm.put(hash, user);
        sendConfirmMessage(userDto.getEmail(), hash);
    }

    public void confirmAndCreate(int hash) {
        if (waitingForConfirm.containsKey(hash)) {
            User user = waitingForConfirm.get(hash);
            user.setActivated(true);
            if (user.getCreatedAt().getTime() - new Date().getTime() < ONE_HOUR_IN_MILLIS) {
                userRepository.save(user);
            }
        }
    }

    private void sendConfirmMessage(String email, int hash) {
        try {
            mailSender.sendEmail(email, "News agency registration",
                    "Hello! Now you should confirm yourself via this link:" +
                            "http://localhost:8080/register/confirm/" + hash);
        } catch (MessagingException e) {
            throw new IncorrectEmailDataException(e);
        }
    }

    private String cryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
