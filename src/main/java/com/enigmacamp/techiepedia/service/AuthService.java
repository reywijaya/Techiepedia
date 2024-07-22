package com.enigmacamp.techiepedia.service;

import com.enigmacamp.techiepedia.model.dto.request.AuthRequest;
import com.enigmacamp.techiepedia.model.dto.request.CustomerRequest;
import com.enigmacamp.techiepedia.model.dto.response.LoginResponse;
import com.enigmacamp.techiepedia.model.dto.response.RegisterResponse;
import com.enigmacamp.techiepedia.model.entities.Admin;
import com.enigmacamp.techiepedia.model.entities.Seller;

public interface AuthService {
    LoginResponse login(AuthRequest<String> authRequest);
    RegisterResponse customerRegistration(AuthRequest<CustomerRequest> authRequest);
    RegisterResponse adminRegistration(AuthRequest<Admin> authRequest);
    RegisterResponse sellerRegistration(AuthRequest<Seller> authRequest);
}