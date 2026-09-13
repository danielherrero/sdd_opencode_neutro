package com.example.department.exception;

import com.example.user.exception.ErrorResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DepartmentIntegrityConflictExceptionMapper
        implements ExceptionMapper<DepartmentIntegrityConflictException> {

    @Override
    public Response toResponse(DepartmentIntegrityConflictException exception) {
        return Response.status(Response.Status.CONFLICT)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }
}
