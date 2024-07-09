package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}