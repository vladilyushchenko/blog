package com.leverx.blog.auth.registration;

import com.leverx.blog.dao.UserDao;
import com.leverx.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RegistrationService {
    private final UserDao userDao;
    private final MailSenderService mailSender;
    private final Map<Integer, User> waitingForConfirm = new ConcurrentHashMap<>();

    @Autowired
    public RegistrationService(UserDao userDao, MailSenderService mailSender) {
        this.userDao = userDao;
        this.mailSender = mailSender;
    }

    public void register(RegistrationForm form) throws UserAlreadyExistsException, MessagingException {
        if (containsUser(form.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered!");
        }
        User newUser = new User();
        newUser.setFirstName(form.getFirstName());
        newUser.setLastName(form.getLastName());
        newUser.setEmail(form.getEmail());
        newUser.setPassword(form.getPassword());
        newUser.setCreatedAt(new Date());

        int hash = newUser.hashCode();
        sendConfirmMessage(form.getEmail(), hash);
        waitingForConfirm.put(hash, newUser);
    }

    public void confirmAndCreate(int hash) {
        userDao.create(waitingForConfirm.remove(hash));
    }

    private boolean containsUser(String email) {
        return userDao.getByEmail(email) != null;
    }

    private void sendConfirmMessage(String email, int hash) throws MessagingException {
        mailSender.sendEmail(email, "News agency registration",
                "Hello! Now you should confirm yourself via this link:" +
                        "http://localhost:8080/register/confirm/" + hash);
    }
}
