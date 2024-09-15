package com.example.register.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.register.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
