package com.leverx.blog.services;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@PropertySource(value = "classpath:mail.properties")
public class MailSenderService {
    private final Properties props;
    private final String user;
    private final String password;

    public MailSenderService(Environment environment) {
        props = new Properties();
        props.put("mail.smtp.host", environment.getRequiredProperty("mail.smtp.host"));
        props.put("mail.smtp.port", environment.getRequiredProperty("mail.smtp.port"));
        props.put("mail.smtp.auth", environment.getRequiredProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", environment
                .getRequiredProperty("mail.smtp.starttls.enable"));
        user = environment.getRequiredProperty("mail.user");
        password = environment.getRequiredProperty("mail.password");
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}
