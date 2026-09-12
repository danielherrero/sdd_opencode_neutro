package com.example.userdepartment.entity;

import java.time.LocalDate;
import com.example.department.entity.Department;
import com.example.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_departments", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "department_id" }))
public class UserDepartment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id", nullable = false) private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "department_id", nullable = false) private Department department;
    @Column(name = "fecha_creacion", nullable = false) private LocalDate fechaCreacion;
    protected UserDepartment() {}
    public UserDepartment(User user, Department department, LocalDate fechaCreacion) { this.user = user; this.department = department; this.fechaCreacion = fechaCreacion; }
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Department getDepartment() { return department; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void update(User user, Department department) { if (user != null) this.user = user; if (department != null) this.department = department; }
}
