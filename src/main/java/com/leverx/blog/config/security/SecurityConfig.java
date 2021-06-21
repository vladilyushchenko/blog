package com.leverx.blog.config.security;

import com.leverx.blog.entities.Role;
import com.leverx.blog.services.security.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserSecurityService securityService;

    @Autowired
    public SecurityConfig(UserSecurityService securityService) {
        this.securityService = securityService;
    }

    // for auth: POST-method on /login
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/my").hasAnyRole(Role.USER_ROLE.getName(),
                                                                        Role.ADMIN_ROLE.getName())
                .antMatchers(HttpMethod.GET,"/articles/**").hasAnyRole(Role.USER_ROLE.getName(),
                                                                                Role.ADMIN_ROLE.getName())
                .antMatchers(HttpMethod.DELETE, "/articles/**")
                    .hasAnyRole(Role.USER_ROLE.getName(), Role.ADMIN_ROLE.getName())
                .antMatchers(HttpMethod.POST, "/articles").hasAnyRole(Role.USER_ROLE.getName(),
                                                                            Role.ADMIN_ROLE.getName())
                .and()
                .formLogin()
                .and()
                .logout()
                    .logoutSuccessUrl("/");
//                    .loginProcessingUrl("/login")
//                    .defaultSuccessUrl("/")
//                    .failureUrl("/fail");
//            .and()
//                .logout()
//                    .logoutUrl("/logout");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(securityService);

        return authenticationProvider;
    }

}