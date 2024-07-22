package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.dto.response.SellerResponse;
import com.enigmacamp.techiepedia.model.entities.Seller;
import com.enigmacamp.techiepedia.repository.SellerRepository;
import com.enigmacamp.techiepedia.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Override
    public SellerResponse createSeller(Seller seller) {
        return convertToSellerResponse(sellerRepository.save(seller));
    }

    private SellerResponse convertToSellerResponse(Seller seller) {
        return SellerResponse.builder()
                .id(seller.getId())
                .name(seller.getName())
                .email(seller.getEmail())
                .phone(seller.getPhone())
                .address(seller.getAddress())
                .build();
    }
}