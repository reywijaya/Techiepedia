package com.enigmacamp.techiepedia.controller;

import com.enigmacamp.techiepedia.constant.APIUrl;
import com.enigmacamp.techiepedia.model.dto.request.ProductRequest;
import com.enigmacamp.techiepedia.model.dto.response.ProductResponse;
import com.enigmacamp.techiepedia.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = APIUrl.PRODUCT_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
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

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/add-product")
    public ProductResponse addProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    @PatchMapping("/update-product")
    public ProductResponse updateProduct(@RequestBody ProductRequest request) {
        return productService.updatePut(request);
    }

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    @PatchMapping("/update-stock")
    public ProductResponse updateStock(@RequestBody ProductRequest request) {
        return productService.updatePatch(request);
    }

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    @PostMapping("/save-all")
    public HttpStatus saveAllProducts(@RequestBody List<ProductRequest> requests) {
        productService.saveAllProducts(requests);
        return HttpStatus.CREATED;
    }
}