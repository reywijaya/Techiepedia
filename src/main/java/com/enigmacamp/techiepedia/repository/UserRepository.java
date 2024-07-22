package com.enigmacamp.techiepedia.repository;

import com.enigmacamp.techiepedia.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}