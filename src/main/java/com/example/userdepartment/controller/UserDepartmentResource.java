package com.example.userdepartment.controller;
import java.net.URI; import java.util.List;
import com.example.userdepartment.dto.*; import com.example.userdepartment.service.UserDepartmentService;
import jakarta.validation.Valid; import jakarta.ws.rs.*; import jakarta.ws.rs.core.*;
@Path("/user-departments") @Consumes(MediaType.APPLICATION_JSON) @Produces(MediaType.APPLICATION_JSON)
public class UserDepartmentResource {
    private final UserDepartmentService service; public UserDepartmentResource(UserDepartmentService service){this.service=service;}
    @POST public Response create(@Valid CreateUserDepartmentRequest r){UserDepartmentResponse x=service.create(r); return Response.created(URI.create("/user-departments/"+x.id())).entity(x).build();}
    @GET public List<UserDepartmentResponse> list(){return service.findAll();}
    @GET @Path("/{id}") public UserDepartmentResponse get(@PathParam("id") Long id){return service.find(id);}
    @PUT @Path("/{id}") public UserDepartmentResponse update(@PathParam("id") Long id, UpdateUserDepartmentRequest r){return service.update(id,r);}
    @DELETE @Path("/{id}") public Response delete(@PathParam("id") Long id){service.delete(id);return Response.noContent().build();}
}
