package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.model.Product;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(String name, ProductRequest request);

    void deleteProduct(String name);

    Product getProductByName(String name);

    List<ProductResponse> getAllProducts();
}