package com.example.department.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UpdateDepartmentRequest(
        @Size(min = 1, message = "no puede estar vacio") String nombre,
        String descripcion,
        LocalDate fechaCreacion) {

    public UpdateDepartmentRequest {
        nombre = trim(nombre);
        descripcion = trim(descripcion);
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
