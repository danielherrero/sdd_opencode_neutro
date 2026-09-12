package com.example.user.exception;

import java.util.Map;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Map<String, String> errors = exception.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> fieldName(violation.getPropertyPath().toString()),
                        violation -> violation.getMessage(),
                        (first, ignored) -> first));

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("La solicitud contiene campos invalidos", errors))
                .build();
    }

    private String fieldName(String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }
}
