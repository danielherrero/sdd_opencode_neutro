package com.example.department.exception;

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(Long id) {
        super("No existe el departamento con id " + id);
    }
}
