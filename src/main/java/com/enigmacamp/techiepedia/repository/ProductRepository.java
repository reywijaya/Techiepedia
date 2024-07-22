package com.enigmacamp.techiepedia.repository;

import com.enigmacamp.techiepedia.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByDeletedFalse();
}