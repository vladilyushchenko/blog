package com.leverx.blog.exception;

public class IncorrectEmailDataException extends RuntimeException {
    public IncorrectEmailDataException(Exception exc) {
        super(exc);
    }
}
