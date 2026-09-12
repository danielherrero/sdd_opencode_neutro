package com.example.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.user.exception.ErrorResponse;
import com.example.user.exception.UnexpectedExceptionMapper;

import jakarta.ws.rs.core.Response;

class UnexpectedExceptionMapperTest {

    @Test
    @DisplayName("T100 devuelve un error 500 sin detalles internos")
    void shouldReturnGenericInternalServerError() {
        Response response = new UnexpectedExceptionMapper().toResponse(new IllegalStateException("detalle interno"));

        assertEquals(500, response.getStatus());
        assertEquals("Error interno del servidor", ((ErrorResponse) response.getEntity()).error());
    }
}
