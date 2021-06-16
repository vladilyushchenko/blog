package com.leverx.blog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@ComponentScan("com.leverx.blog")
@EnableWebMvc
public class SpringMvcConfig {

}
