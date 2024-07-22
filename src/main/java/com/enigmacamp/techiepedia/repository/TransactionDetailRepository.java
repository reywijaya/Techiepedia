package com.enigmacamp.techiepedia.repository;

import com.enigmacamp.techiepedia.model.entities.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
}
