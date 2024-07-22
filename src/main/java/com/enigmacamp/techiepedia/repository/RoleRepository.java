package com.enigmacamp.techiepedia.repository;

import com.enigmacamp.techiepedia.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(Role.ERole role);
}