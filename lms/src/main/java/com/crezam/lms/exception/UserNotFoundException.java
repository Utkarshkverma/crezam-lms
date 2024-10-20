package com.crezam.lms.exception;

public class UserNotFoundException extends RuntimeException {

    String id;

    public UserNotFoundException(String id) {
        super(String.format("User with id %s not found", id));
    }
}
