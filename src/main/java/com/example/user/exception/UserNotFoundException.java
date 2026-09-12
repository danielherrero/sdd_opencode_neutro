package com.example.user.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("No existe el usuario con id " + id);
    }
}
