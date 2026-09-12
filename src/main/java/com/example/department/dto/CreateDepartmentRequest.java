package com.example.department.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record CreateDepartmentRequest(
        @NotBlank(message = "es obligatorio y no puede estar vacio") String nombre,
        String descripcion,
        LocalDate fechaCreacion) {

    public CreateDepartmentRequest {
        nombre = trim(nombre);
        descripcion = trim(descripcion);
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
