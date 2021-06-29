package com.leverx.blog.exception;

public class UserNotActivatedException extends RuntimeException {
    public UserNotActivatedException(String msg) {
        super(msg);
    }
}