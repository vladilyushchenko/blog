package com.leverx.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:mail.yaml")
public class MailConfig {
    @Bean
    public Properties mailProperties(Environment environment) {
        Properties props = new Properties();
        props.put("mail.smtp.host", environment.getProperty("host"));
        props.put("mail.smtp.port", environment.getProperty("port"));
        props.put("mail.smtp.auth", environment.getProperty("auth"));
        props.put("mail.smtp.starttls.enable", environment
                .getRequiredProperty("starttls.enable"));
        return props;
    }
}
