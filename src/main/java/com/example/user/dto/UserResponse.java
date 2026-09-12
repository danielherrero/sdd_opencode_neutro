package com.example.user.dto;

import java.time.LocalDate;

public record UserResponse(Long id, String nombre, String apellidos, LocalDate fechaNacimiento) {
}
