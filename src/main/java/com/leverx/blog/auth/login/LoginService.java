//package com.leverx.blog.auth.login;
//
//import com.leverx.blog.dao.UserDao;
//import com.leverx.blog.dto.LoginDto;
//import com.leverx.blog.entities.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class LoginService {
//    private final UserDao userDao;
//
//    @Autowired
//    public LoginService(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    public boolean tryLogin(LoginDto loginDto) {
//        Optional<User> user = userDao.getByEmail(loginDto.getEmail());
//        return user.isPresent() && user.get().getPassword().equals(loginDto.getPassword());
//    }
//}
