package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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

    @PostMapping("/add-product")
    public ProductResponse addProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PatchMapping("/update-product")
    public ProductResponse updateProduct(@RequestBody ProductRequest request) {
        return productService.updatePut(request);
    }

    @PatchMapping("/update-stock")
    public ProductResponse updateStock(@RequestBody ProductRequest request) {
        return productService.updatePatch(request);
    }

    @PostMapping("/save-all")
    public HttpStatus saveAllProducts(@RequestBody List<ProductRequest> requests) {
        productService.saveAllProducts(requests);
        return HttpStatus.CREATED;
    }

    private CommonResponse<ProductResponse> generateProductResponse(Integer code, String message, Optional<ProductResponse> productResponse) {
        return CommonResponse.<ProductResponse>builder()
                .statusCode(code)
                .message(message)
                .data(productResponse)
                .build();
    }
}