package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.Products;
import com.enigmacamp.tokonyadia.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void addProduct(Products product){
        productRepo.save(product);
    }

    public void updateProduct(Products product){
        productRepo.save(product);
    }

    public List<Products> getAllProducts(){
        return productRepo.findAll();
    }

    public void deleteProduct(Long id){
        productRepo.deleteById(id);
    }
}