package com.example.department.controller;

import java.net.URI;
import java.util.List;

import com.example.department.dto.CreateDepartmentRequest;
import com.example.department.dto.DepartmentResponse;
import com.example.department.dto.UpdateDepartmentRequest;
import com.example.department.service.DepartmentService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/departments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    private final DepartmentService service;

    public DepartmentResource(DepartmentService service) {
        this.service = service;
    }

    @POST
    public Response create(@Valid CreateDepartmentRequest request) {
        DepartmentResponse response = service.create(request);
        return Response.created(URI.create("/departments/" + response.id())).entity(response).build();
    }

    @GET
    public List<DepartmentResponse> list() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public DepartmentResponse get(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @PUT
    @Path("/{id}")
    public DepartmentResponse update(@PathParam("id") Long id, @Valid UpdateDepartmentRequest request) {
        return service.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
