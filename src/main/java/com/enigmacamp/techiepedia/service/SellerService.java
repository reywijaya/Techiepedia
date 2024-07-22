package com.enigmacamp.techiepedia.service;

import com.enigmacamp.techiepedia.model.dto.response.SellerResponse;
import com.enigmacamp.techiepedia.model.entities.Seller;

public interface SellerService {
    SellerResponse createSeller(Seller seller);
}