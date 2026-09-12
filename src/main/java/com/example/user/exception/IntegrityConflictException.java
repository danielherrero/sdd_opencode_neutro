package com.example.user.exception;

public class IntegrityConflictException extends RuntimeException {

    public IntegrityConflictException() {
        super("No se puede eliminar el usuario porque esta referenciado por otros datos");
    }
}
