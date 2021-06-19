package com.leverx.blog.config.security;

import com.leverx.blog.entities.enums.UserRole;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/users/**").permitAll()
                .antMatchers(HttpMethod.GET,"/my").hasRole(UserRole.USER.name())
                .antMatchers(HttpMethod.DELETE, "/article/").hasRole(UserRole.USER.name());
    }

}