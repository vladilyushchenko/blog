package com.leverx.blog.services.authorization;

import com.leverx.blog.repositories.UserRepository;
import com.leverx.blog.dto.PasswordResetDto;
import com.leverx.blog.entities.User;
import com.leverx.blog.exceptions.IncorrectEmailDataException;
import com.leverx.blog.exceptions.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PasswordService {
    private final Map<Integer, String> waitingForReset = new ConcurrentHashMap<>();
    private final UserRepository userRepository;
    private final MailSenderService mailSender;
    private final PasswordEncoder passwordEncoder;

    public PasswordService(UserRepository userRepository, MailSenderService mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void reset(String email) {
        if (!userExists(email)) {
            throw new NotFoundException(String.format("User with email %s doesnt exist!", email));
        }
        int hash = email.hashCode();
        sendConfirmMessage(email, hash);
        waitingForReset.put(hash, email);
    }

    public void confirmReset(PasswordResetDto resetDto) {
        if (!waitingForReset.containsKey(resetDto.getHash())) {
            throw new NotFoundException(String.format("It's no password-update request with hash %d",
                    resetDto.getHash()));
        }
        String email = waitingForReset.remove(resetDto.getHash());
        User user = userRepository.findUserByEmail(email).get();
        user.setPassword(cryptPassword(resetDto.getPassword()));
        userRepository.save(user);
        // in future make own query with @Query annotation
    }

    private boolean userExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    private void sendConfirmMessage(String email, int hash) {
        try {
            mailSender.sendEmail(email, "News agency password reset",
                    "Hello! Make POST-request to reset your password via this link:" +
                            "http://localhost:8080/auth/reset/" + hash);
        } catch (MessagingException e) {
            throw new IncorrectEmailDataException(e);
        }
    }

    private String cryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
