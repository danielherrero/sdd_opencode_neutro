package com.example.department.service;

import java.time.LocalDate;
import java.util.List;

import com.example.department.dto.CreateDepartmentRequest;
import com.example.department.dto.DepartmentResponse;
import com.example.department.dto.UpdateDepartmentRequest;
import com.example.department.entity.Department;
import com.example.department.exception.DepartmentNotFoundException;
import com.example.department.repository.DepartmentRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public DepartmentResponse create(CreateDepartmentRequest request) {
        Department department = new Department(request.nombre(), request.descripcion(),
                request.fechaCreacion() == null ? LocalDate.now() : request.fechaCreacion());
        repository.persist(department);
        return toResponse(department);
    }

    public DepartmentResponse findById(Long id) {
        return toResponse(findEntity(id));
    }

    public List<DepartmentResponse> findAll() {
        return repository.listAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public DepartmentResponse update(Long id, UpdateDepartmentRequest request) {
        Department department = findEntity(id);
        department.update(request.nombre(), request.descripcion(), request.fechaCreacion());
        return toResponse(department);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new DepartmentNotFoundException(id);
        }
    }

    private Department findEntity(Long id) {
        Department department = repository.findById(id);
        if (department == null) {
            throw new DepartmentNotFoundException(id);
        }
        return department;
    }

    private DepartmentResponse toResponse(Department department) {
        return new DepartmentResponse(department.getId(), department.getNombre(), department.getDescripcion(),
                department.getFechaCreacion());
    }
}
