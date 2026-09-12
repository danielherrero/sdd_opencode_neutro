package com.example.userdepartment.exception;
import com.example.user.exception.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.*;
@Provider public class ReferencedEntityNotFoundExceptionMapper implements ExceptionMapper<ReferencedEntityNotFoundException> { public Response toResponse(ReferencedEntityNotFoundException e) { return Response.status(404).entity(new ErrorResponse(e.getMessage())).build(); } }
