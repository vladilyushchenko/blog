package com.leverx.blog.auth.registration;

import com.leverx.blog.dao.UserDao;
import com.leverx.blog.dto.UserDto;
import com.leverx.blog.dto.UserMapping;
import com.leverx.blog.entities.User;
import com.leverx.blog.services.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistrationService {
    private final UserDao userDao;
    private final MailSenderService mailSender;
    private final Map<Integer, User> waitingForConfirm = new ConcurrentHashMap<>();

    @Autowired
    public RegistrationService(UserDao userDao, MailSenderService mailSender) {
        this.userDao = userDao;
        this.mailSender = mailSender;
    }

    public void register(UserDto userDto) throws UserAlreadyExistsException, MessagingException {
        if (containsUser(userDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered!");
        }
        User newUser = UserMapping.mapToUserEntity(userDto);
        newUser.setCreatedAt(new Date());

        int hash = newUser.hashCode();
        sendConfirmMessage(userDto.getEmail(), hash);
        waitingForConfirm.put(hash, newUser);
    }

    public void confirmAndCreate(int hash) {
        userDao.create(waitingForConfirm.remove(hash));
    }

    private boolean containsUser(String email) {
        return userDao.getByEmail(email).isPresent();
    }

    private void sendConfirmMessage(String email, int hash) throws MessagingException {
        mailSender.sendEmail(email, "News agency registration",
                "Hello! Now you should confirm yourself via this link:" +
                        "http://localhost:8080/register/confirm/" + hash);
    }
}
