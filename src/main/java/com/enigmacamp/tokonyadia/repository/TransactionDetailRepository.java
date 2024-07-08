package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
}
