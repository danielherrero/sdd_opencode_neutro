package com.example.user.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record UpdateUserRequest(
        @Size(min = 1, message = "no puede estar vacio") @Pattern(regexp = "\\p{L}+", message = "solo puede contener letras") String nombre,
        @Size(min = 1, message = "no puede estar vacio") @Pattern(regexp = "\\p{L}+", message = "solo puede contener letras") String apellidos,
        @Adult LocalDate fechaNacimiento,
        @Size(min = 1, message = "no puede estar vacia") String contrasena) {

    public UpdateUserRequest {
        nombre = trim(nombre);
        apellidos = trim(apellidos);
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
