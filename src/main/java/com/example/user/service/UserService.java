package com.example.user.service;

import java.util.List;

import com.example.user.dto.CreateUserRequest;
import com.example.user.dto.UpdateUserRequest;
import com.example.user.dto.UserResponse;
import com.example.user.entity.User;
import com.example.user.exception.UserNotFoundException;
import com.example.user.exception.IntegrityConflictException;
import com.example.user.repository.UserRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public UserService(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        User user = new User(request.nombre(), request.apellidos(), request.fechaNacimiento(),
                BcryptUtil.bcryptHash(request.contrasena()));
        userRepository.persist(user);
        return toResponse(user);
    }

    public UserResponse findById(Long id) {
        return toResponse(findEntity(id));
    }

    public List<UserResponse> findAll() {
        return userRepository.listAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = findEntity(id);
        user.update(request.nombre(), request.apellidos(), request.fechaNacimiento());
        if (request.contrasena() != null) {
            user.updateContrasena(BcryptUtil.bcryptHash(request.contrasena()));
        }
        return toResponse(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = findEntity(id);
        try {
            entityManager.remove(user);
            entityManager.flush();
        } catch (PersistenceException exception) {
            throw new IntegrityConflictException();
        }
    }

    private User findEntity(Long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getNombre(), user.getApellidos(), user.getFechaNacimiento());
    }
}
