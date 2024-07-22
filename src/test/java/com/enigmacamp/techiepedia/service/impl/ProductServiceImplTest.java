package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.dto.request.ProductRequest;
import com.enigmacamp.techiepedia.model.dto.response.ProductResponse;
import com.enigmacamp.techiepedia.model.entities.Product;
import com.enigmacamp.techiepedia.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = Product.builder()
                .id("1")
                .name("Raw Jeans")
                .price(100)
                .stock(10)
                .category("Jeans")
                .deleted(false)
                .build();

        productRequest = ProductRequest.builder()
                .id("1")
                .name("Raw Jeans")
                .price(100)
                .stock(10)
                .category("Jeans")
                .build();
    }

    @Test
    void createProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.createProduct(productRequest);

        assertNotNull(response);
        assertEquals(productRequest.getId(), response.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteProduct() {
        when(productRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(product));
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product); // Use when...thenReturn

        productService.deleteProduct("1");

        assertTrue(product.getDeleted());
        verify(productRepository, times(1)).findAllByDeletedFalse();
        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
    }

    @Test
    void getAllProducts() {
        when(productRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(product));

        List<ProductResponse> responses = productService.getAllProducts();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(productRepository, times(1)).findAllByDeletedFalse();
    }

    @Test
    void getProductById() {
        when(productRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(product));

        ProductResponse response = productService.getProductById("1");

        assertNotNull(response);
        assertEquals(product.getId(), response.getId());
        verify(productRepository, times(1)).findAllByDeletedFalse();
    }

    @Test
    void saveAllProducts() {
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product); // Use when...thenReturn

        productService.saveAllProducts(Collections.singletonList(productRequest));

        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
    }

    @Test
    void updatePut() {
        when(productRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(product));
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.updatePut(productRequest);

        assertNotNull(response);
        assertEquals(productRequest.getId(), response.getId());
        verify(productRepository, times(1)).findAllByDeletedFalse();
        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
    }

    @Test
    void updatePatch() {
        when(productRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(product));
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product);

        ProductRequest updateRequest = new ProductRequest("1", "Washed Denim", "Jeans", 20, 200);
        ProductResponse response = productService.updatePatch(updateRequest);

        assertNotNull(response);
        assertEquals(updateRequest.getId(), response.getId());
        assertEquals(updateRequest.getName(), response.getName());
        assertEquals(updateRequest.getPrice(), response.getPrice());
        assertEquals(updateRequest.getStock(), response.getStock());
        assertEquals(updateRequest.getCategory(), response.getCategory());
        verify(productRepository, times(1)).findAllByDeletedFalse();
        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
    }

    @Test
    void findByIdOrThrow() {
        when(productRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(product));

        Product result = productService.findByIdOrThrow("1");

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        verify(productRepository, times(1)).findAllByDeletedFalse();
    }

    @Test
    void findByIdOrThrow_NotFound() {
        when(productRepository.findAllByDeletedFalse()).thenReturn(Collections.emptyList());

        assertThrows(ResponseStatusException.class, () -> productService.findByIdOrThrow("1"));
        verify(productRepository, times(1)).findAllByDeletedFalse();
    }
}