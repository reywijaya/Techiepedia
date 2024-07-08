package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.model.Product;
import com.enigmacamp.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @DeleteMapping("/delete-product")
    public void deleteProduct(@RequestParam(name = "id") String id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/product")
    public ProductResponse getProductById(@RequestParam(name = "id") String id) {
        return convertToProductResponse(productService.getProductById(id));
    }

    @GetMapping("/all-products")
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProducts().stream().map(this::convertToProductResponse).toList();
    }

    @PostMapping("/add-product")
    public ProductResponse addProduct(@RequestBody ProductRequest request) {
        return convertToProductResponse(productService.createProduct(request));
    }

    @PatchMapping("/update-product")
    public ProductResponse updateProduct(@RequestBody ProductRequest request) {
        return convertToProductResponse(productService.updateProduct(request));
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock()).build();
    }
}