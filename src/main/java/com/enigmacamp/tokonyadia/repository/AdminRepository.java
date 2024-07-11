package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}