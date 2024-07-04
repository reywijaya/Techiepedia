package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value = "customer")
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}