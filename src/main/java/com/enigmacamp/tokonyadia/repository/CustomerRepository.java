package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.entities.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("customer")
public interface CustomerRepository extends JpaRepository<Customer, String> {
}