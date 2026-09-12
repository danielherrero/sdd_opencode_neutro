package com.example.user.controller;

import java.net.URI;
import java.util.List;

import com.example.user.dto.CreateUserRequest;
import com.example.user.dto.UpdateUserRequest;
import com.example.user.dto.UserResponse;
import com.example.user.service.UserService;

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

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    public Response create(@Valid CreateUserRequest request) {
        UserResponse response = userService.create(request);
        return Response.created(URI.create("/users/" + response.id())).entity(response).build();
    }

    @GET
    public List<UserResponse> list() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    public UserResponse get(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @PUT
    @Path("/{id}")
    public UserResponse update(@PathParam("id") Long id, @Valid UpdateUserRequest request) {
        return userService.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        userService.delete(id);
        return Response.noContent().build();
    }
}
