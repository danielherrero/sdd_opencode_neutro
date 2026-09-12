package com.example.userdepartment.exception;
public class ReferencedEntityNotFoundException extends RuntimeException { public ReferencedEntityNotFoundException(String field, Long id) { super(field + " no existe: " + id); } }
