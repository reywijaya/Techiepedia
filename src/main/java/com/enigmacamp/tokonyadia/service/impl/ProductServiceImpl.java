package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.Product;
import com.enigmacamp.tokonyadia.repository.ProductRepository;
import com.enigmacamp.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(ProductRequest request) {
        Product product = convertProductRequestToProductEntity(request);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(ProductRequest request) {
        findByIdOrThrow(request.getId());
        Product product = convertProductRequestToProductEntity(request);
        productRepository.saveAndFlush(product);
        return product;
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        return findByIdOrThrow(id);
    }

    private Product findByIdOrThrow(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Product convertProductRequestToProductEntity(ProductRequest request) {
        return Product.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }
}