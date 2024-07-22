package com.enigmacamp.techiepedia.repository;

import com.enigmacamp.techiepedia.model.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}