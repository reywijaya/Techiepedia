package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.entities.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequest request);
    Product updateProduct(ProductRequest request);
    void deleteProduct(String id);
    List<Product> getAllProducts();
    Product getProductById(String id);
}