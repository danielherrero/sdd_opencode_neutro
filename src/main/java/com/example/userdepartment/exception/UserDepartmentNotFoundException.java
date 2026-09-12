package com.example.userdepartment.exception;
public class UserDepartmentNotFoundException extends RuntimeException { public UserDepartmentNotFoundException(Long id) { super("No existe la asociacion con id " + id); } }
