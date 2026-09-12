package com.example.user.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateUserRequest(
        @NotBlank(message = "es obligatorio y no puede estar vacio") @Pattern(regexp = "\\p{L}+", message = "solo puede contener letras") String nombre,
        @NotBlank(message = "es obligatorio y no puede estar vacio") @Pattern(regexp = "\\p{L}+", message = "solo puede contener letras") String apellidos,
        @NotNull(message = "es obligatoria y debe tener el formato AAAA-MM-DD") @Adult LocalDate fechaNacimiento,
        @NotBlank(message = "es obligatoria y no puede estar vacia") String contrasena) {

    public CreateUserRequest {
        nombre = trim(nombre);
        apellidos = trim(apellidos);
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
