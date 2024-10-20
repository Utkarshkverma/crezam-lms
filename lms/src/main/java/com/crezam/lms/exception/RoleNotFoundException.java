package com.crezam.lms.exception;

public class RoleNotFoundException extends RuntimeException {

    String role;
    public RoleNotFoundException(String role) {
        super(String.format("Role %s not found", role));
    }
}
