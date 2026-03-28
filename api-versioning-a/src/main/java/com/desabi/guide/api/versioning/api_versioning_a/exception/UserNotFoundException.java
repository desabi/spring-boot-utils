package com.desabi.guide.api.versioning.api_versioning_a.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}