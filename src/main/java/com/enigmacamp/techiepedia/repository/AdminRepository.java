package com.enigmacamp.techiepedia.repository;

import com.enigmacamp.techiepedia.model.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}