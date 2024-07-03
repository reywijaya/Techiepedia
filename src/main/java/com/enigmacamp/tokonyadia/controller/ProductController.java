package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.model.Products;
import com.enigmacamp.tokonyadia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    List<Products> products;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all-products")
    public List<Products> getAllProduct() {
        return productService.getAllProducts();
    }

    @PostMapping("/add-product")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody Products product) {
        productService.addProduct(product);
    }

    @PutMapping("/update-product/{id}")
    public void updateProduct(@PathVariable Long id) {
        Products updatedProduct = products.stream().filter(product -> product.getId().equals(id)).findFirst().orElseThrow();
        productService.updateProduct(updatedProduct);
    }

    @DeleteMapping("/delete-product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}