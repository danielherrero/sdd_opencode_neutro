package com.example.user.exception;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Override
    public Response toResponse(JsonMappingException exception) {
        String field = exception.getPath().isEmpty()
                ? "solicitud"
                : exception.getPath().get(exception.getPath().size() - 1).getFieldName();
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("La solicitud contiene campos invalidos",
                        Map.of(field, "tiene un formato no valido")))
                .build();
    }
}
