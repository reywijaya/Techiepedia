package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.model.Product;
import com.enigmacamp.tokonyadia.repository.ProductRepository;
import com.enigmacamp.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier(value = "product")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        productRepository.save(product);

        ProductResponse response = new ProductResponse();
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        return response;
    }

    @Override
    public ProductResponse updateProduct(String name, ProductRequest request) {

        Product product = productRepository.updateProductByNameContainingIgnoreCase(name, request);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setStock(product.getStock());

        return productResponse;
    }

    @Override
    public void deleteProduct(String name) {
        productRepository.deleteByNameContainingIgnoreCase(name);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            ProductResponse response = new ProductResponse();
            response.setName(product.getName());
            response.setPrice(product.getPrice());
            response.setStock(product.getStock());
            productResponses.add(response);
        }
        return productResponses;
    }
}