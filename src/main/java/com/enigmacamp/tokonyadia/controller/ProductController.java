package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @DeleteMapping("/delete-product")
    public void deleteProduct(@RequestParam(name = "id") String id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/product")
    public ResponseEntity<ProductResponse> getProduct(@RequestParam(name = "id") String id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping("/all-products")
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProducts();
    }

    @PostMapping("/add-product")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PatchMapping("/update-product")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(request));
    }
}