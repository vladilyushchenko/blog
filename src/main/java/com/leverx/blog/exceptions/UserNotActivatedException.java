package com.leverx.blog.exceptions;

public class UserNotActivatedException extends RuntimeException {
    public UserNotActivatedException(String msg) {
        super(msg);
    }
}