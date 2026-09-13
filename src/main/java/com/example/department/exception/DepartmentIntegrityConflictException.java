package com.example.department.exception;

public class DepartmentIntegrityConflictException extends RuntimeException {

    public DepartmentIntegrityConflictException() {
        super("No se puede eliminar el departamento porque esta referenciado por otros datos");
    }
}
