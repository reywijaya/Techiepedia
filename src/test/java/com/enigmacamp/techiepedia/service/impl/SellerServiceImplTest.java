package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.dto.response.SellerResponse;
import com.enigmacamp.techiepedia.model.entities.Seller;
import com.enigmacamp.techiepedia.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SellerServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    private Seller seller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seller = Seller.builder()
                .id("1")
                .name("Seller Name")
                .email("seller@example.com")
                .phone("123456789")
                .address("Seller Address")
                .build();
    }

    @Test
    void createSeller() {
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);

        SellerResponse sellerResponse = sellerService.createSeller(seller);

        assertEquals(seller.getId(), sellerResponse.getId());
        assertEquals(seller.getName(), sellerResponse.getName());
        assertEquals(seller.getEmail(), sellerResponse.getEmail());
        assertEquals(seller.getPhone(), sellerResponse.getPhone());
        assertEquals(seller.getAddress(), sellerResponse.getAddress());

        verify(sellerRepository, times(1)).save(any(Seller.class));
    }
}