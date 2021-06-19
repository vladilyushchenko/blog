package com.leverx.blog.exceptions;

public class IncorrectEmailDataException extends RuntimeException {
    public IncorrectEmailDataException(Exception exc) {
        super(exc);
    }
}
