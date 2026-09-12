package com.example.department.dto;

import java.time.LocalDate;

public record DepartmentResponse(Long id, String nombre, String descripcion, LocalDate fechaCreacion) {
}
