package com.example.user.repository;

import com.example.user.entity.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findUserById(Long id) {
        return find("id", id).firstResult();
    }
}
