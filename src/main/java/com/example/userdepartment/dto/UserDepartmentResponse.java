package com.example.userdepartment.dto;
import java.time.LocalDate;
public record UserDepartmentResponse(Long id, Long userId, Long departmentId, LocalDate fechaCreacion) {}
