package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value = "product")
public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByNameContainingIgnoreCase(String name);
    Product findByNameContainingIgnoreCase(String name);
    Product updateProductByNameContainingIgnoreCase(String name, ProductRequest request);
}