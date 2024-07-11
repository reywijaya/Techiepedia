package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.response.SellerResponse;
import com.enigmacamp.tokonyadia.model.entities.Seller;

public interface SellerService {
    SellerResponse createSeller(Seller seller);
}