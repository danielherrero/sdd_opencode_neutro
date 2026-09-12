package com.example.userdepartment.service;

import java.time.LocalDate;
import java.util.List;
import com.example.department.entity.Department;
import com.example.department.repository.DepartmentRepository;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.userdepartment.dto.*;
import com.example.userdepartment.entity.UserDepartment;
import com.example.userdepartment.exception.*;
import com.example.userdepartment.repository.UserDepartmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserDepartmentService {
    private final UserDepartmentRepository repository; private final UserRepository users; private final DepartmentRepository departments;
    public UserDepartmentService(UserDepartmentRepository repository, UserRepository users, DepartmentRepository departments) { this.repository=repository; this.users=users; this.departments=departments; }
    @Transactional public UserDepartmentResponse create(CreateUserDepartmentRequest r) { User u=user(r.userId()); Department d=department(r.departmentId()); UserDepartment x=new UserDepartment(u,d,LocalDate.now()); repository.persist(x); return response(x); }
    public UserDepartmentResponse find(Long id) { return response(entity(id)); }
    public List<UserDepartmentResponse> findAll() { return repository.listAll().stream().map(this::response).toList(); }
    @Transactional public UserDepartmentResponse update(Long id, UpdateUserDepartmentRequest r) { UserDepartment x=entity(id); x.update(r.userId()==null?null:user(r.userId()), r.departmentId()==null?null:department(r.departmentId())); return response(x); }
    @Transactional public void delete(Long id) { if(!repository.deleteById(id)) throw new UserDepartmentNotFoundException(id); }
    private UserDepartment entity(Long id) { UserDepartment x=repository.findById(id); if(x==null) throw new UserDepartmentNotFoundException(id); return x; }
    private User user(Long id) { User x=users.findById(id); if(x==null) throw new ReferencedEntityNotFoundException("userId",id); return x; }
    private Department department(Long id) { Department x=departments.findById(id); if(x==null) throw new ReferencedEntityNotFoundException("departmentId",id); return x; }
    private UserDepartmentResponse response(UserDepartment x) { return new UserDepartmentResponse(x.getId(),x.getUser().getId(),x.getDepartment().getId(),x.getFechaCreacion()); }
}
