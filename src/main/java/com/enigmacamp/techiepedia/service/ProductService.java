package com.enigmacamp.techiepedia.service;

import com.enigmacamp.techiepedia.model.dto.request.ProductRequest;
import com.enigmacamp.techiepedia.model.dto.response.ProductResponse;

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