package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(APIUrl.PRODUCT_API)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @DeleteMapping("/delete-product")
    public HttpStatus deleteProduct(@RequestParam(name = "id") String id) {
        productService.deleteProduct(id);
        return HttpStatus.OK;
    }

    @GetMapping("/product")
    public ProductResponse getProductById(@RequestParam(name = "id") String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/all-products")
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ADMIN')")
    @PostMapping("/add-product")
    public ProductResponse addProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @PatchMapping("/update-product")
    public ProductResponse updateProduct(@RequestBody ProductRequest request) {
        return productService.updatePut(request);
    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @PatchMapping("/update-stock")
    public ProductResponse updateStock(@RequestBody ProductRequest request) {
        return productService.updatePatch(request);
    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @PostMapping("/save-all")
    public HttpStatus saveAllProducts(@RequestBody List<ProductRequest> requests) {
        productService.saveAllProducts(requests);
        return HttpStatus.CREATED;
    }
}