package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, String> {
}