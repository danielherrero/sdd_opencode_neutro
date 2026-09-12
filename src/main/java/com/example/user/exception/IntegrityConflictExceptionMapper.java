package com.example.user.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IntegrityConflictExceptionMapper implements ExceptionMapper<IntegrityConflictException> {

    @Override
    public Response toResponse(IntegrityConflictException exception) {
        return Response.status(Response.Status.CONFLICT).entity(new ErrorResponse(exception.getMessage())).build();
    }
}
