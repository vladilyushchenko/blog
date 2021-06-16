package com.leverx.blog.auth.registration;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
