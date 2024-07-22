package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.dto.request.ProductRequest;
import com.enigmacamp.techiepedia.model.dto.response.ProductResponse;
import com.enigmacamp.techiepedia.model.entities.Product;
import com.enigmacamp.techiepedia.repository.ProductRepository;
import com.enigmacamp.techiepedia.service.ProductService;
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
    public ProductResponse createProduct(ProductRequest request) {
        Product product = convertProductRequestToProductEntity(request);
        productRepository.save(product);
        return convertToProductResponse(product);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = findByIdOrThrow(id);
        product.setDeleted(true);
        productRepository.saveAndFlush(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAllByDeletedFalse().stream().map(this::convertToProductResponse).toList();
    }

    @Override
    public ProductResponse getProductById(String id) {
        return convertToProductResponse(findByIdOrThrow(id));
    }

    @Override
    public void saveAllProducts(List<ProductRequest> requests) {
        requests.stream().map(this::convertProductRequestToProductEntity).forEach(productRepository::saveAndFlush);
    }

    @Override
    public ProductResponse updatePut(ProductRequest request) {
        findByIdOrThrow(request.getId());
        Product product = convertProductRequestToProductEntity(request);
        return convertToProductResponse(productRepository.saveAndFlush(product));
    }

    @Override
    public ProductResponse updatePatch(ProductRequest request) {
        Product existingProduct = findByIdOrThrow(request.getId());
        if (request.getName() != null) { existingProduct.setName(request.getName()); }
        if (request.getStock() != null) { existingProduct.setStock(request.getStock()); }
        if (request.getPrice() != null) { existingProduct.setPrice(request.getPrice()); }
        return convertToProductResponse(productRepository.saveAndFlush(existingProduct));
    }

    Product findByIdOrThrow(String id) {
        List<Product> products = productRepository.findAllByDeletedFalse();
        return products.stream().filter(product -> product.getId().equals(id))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .build();
    }

    private Product convertProductRequestToProductEntity(ProductRequest request) {
        return Product.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(request.getCategory())
                .deleted(false)
                .build();
    }
}