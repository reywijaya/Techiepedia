package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.AuthRequest;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.LoginResponse;
import com.enigmacamp.tokonyadia.model.dto.response.RegisterResponse;
import com.enigmacamp.tokonyadia.model.entities.User;

public interface AuthService {
    LoginResponse login(AuthRequest<String> authRequest);
    RegisterResponse customerRegistration(AuthRequest<CustomerRequest> authRequest);
}