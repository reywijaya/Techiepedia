package com.enigmacamp.techiepedia.repository;

import com.enigmacamp.techiepedia.model.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, String> {
}