package com.leverx.blog.services;

import com.leverx.blog.dao.UserDao;
import com.leverx.blog.dto.UserDto;
import com.leverx.blog.dto.UserMapping;
import com.leverx.blog.entities.User;
import com.leverx.blog.exceptions.IncorrectEmailDataException;
import com.leverx.blog.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistrationService {
    private final UserDao userDao;
    private final MailSenderService mailSender;
    private final Map<Integer, User> waitingForConfirm = new ConcurrentHashMap<>();

    private static final int ONE_HOUR_IN_MILLIS = 86400000;

    @Autowired
    public RegistrationService(UserDao userDao, MailSenderService mailSender) {
        this.userDao = userDao;
        this.mailSender = mailSender;
    }

    public void register(UserDto userDto) {
        if (userAlreadyExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        User user = UserMapping.mapToUserEntity(userDto);
        user.setCreatedAt(new Date());

        int hash = user.hashCode();
        sendConfirmMessage(userDto.getEmail(), hash);

        int id = userDao.save(user);
        waitingForConfirm.put(hash, user);
    }

    public void confirmAndCreate(int hash) {
        if (waitingForConfirm.containsKey(hash)) {
            User user = waitingForConfirm.get(hash);
            if (user.getCreatedAt().getTime() - new Date().getTime() < ONE_HOUR_IN_MILLIS) {
                userDao.updateActivatedById(user.getId());
            }
        }
    }

    private boolean userAlreadyExists(String email) {
        Optional<User> user = userDao.findByEmail(email);
        return user.isPresent() && user.get().isActivated();
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
}
