package com.enigmacamp.techiepedia.repository;

import com.enigmacamp.techiepedia.model.entities.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("customer")
public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findAllByDeletedFalse();
    Page<Customer> findAllByDeletedFalse(Pageable pageable);
}