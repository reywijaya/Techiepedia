package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.model.Product;
import com.enigmacamp.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Qualifier(value = "product")
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController {

    private final ProductService productService;

    @DeleteMapping("/delete-product")
    public void deleteProduct(@RequestParam(name = "name") String name) {
        productService.deleteProduct(name);
    }

    @GetMapping("/product")
    public ProductResponse getProductByName(@RequestParam(name = "name") String name) {
        Product product = productService.getProductByName(name);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setStock(product.getStock());
        return productResponse;
    }

    @GetMapping("/all-products")
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProducts();
    }

    @PostMapping("/add-product")
    public ProductResponse addProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/update-product")
    public ProductResponse updateProduct(@RequestParam(name = "name") String name, @RequestBody ProductRequest request) {
        return productService.updateProduct(name, request);
    }
}