package com.example.department.exception;

import com.example.user.exception.ErrorResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DepartmentNotFoundExceptionMapper implements ExceptionMapper<DepartmentNotFoundException> {

    @Override
    public Response toResponse(DepartmentNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse(exception.getMessage())).build();
    }
}
