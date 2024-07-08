package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.model.Product;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(ProductRequest request);
    void deleteProduct(String id);
    ProductResponse getProduct(String id);
    List<ProductResponse> getAllProducts();
    Product getProductById(String id);
}