package com.leverx.blog.services.authorization;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailSenderService {
    private final Properties props;

    public MailSenderService(Properties mailProperties) {
        this.props = mailProperties;
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.getProperty("user"),
                                props.getProperty("password"));
                    }
                });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(props.getProperty("user")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}
