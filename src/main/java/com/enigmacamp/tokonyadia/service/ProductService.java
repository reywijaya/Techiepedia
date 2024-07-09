package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    void deleteProduct(String id);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(String id);
    void saveAllProducts(List<ProductRequest> requests);
    ProductResponse updatePut(ProductRequest request);
    ProductResponse updatePatch(ProductRequest request);
}