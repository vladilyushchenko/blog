package com.leverx.blog.config;

import com.leverx.blog.dao.UserDao;
import com.leverx.blog.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@ComponentScan("com.leverx.blog")
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {
    private final UserDao userDao;

    public SpringMvcConfig(UserDao userDao) {
        this.userDao = userDao;
    }

//    @Bean
//    public UserDetailsService getUserDetailsService(){
//        return new UserDetailsServiceImpl(userDao);
//    }
}
