package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customers, Long> {
}
