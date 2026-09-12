package com.example.userdepartment.repository;
import com.example.userdepartment.entity.UserDepartment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped public class UserDepartmentRepository implements PanacheRepository<UserDepartment> {}
