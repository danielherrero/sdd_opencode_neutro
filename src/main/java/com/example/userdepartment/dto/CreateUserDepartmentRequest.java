package com.example.userdepartment.dto;
import jakarta.validation.constraints.NotNull;
public record CreateUserDepartmentRequest(@NotNull(message = "es obligatorio") Long userId, @NotNull(message = "es obligatorio") Long departmentId) {}
