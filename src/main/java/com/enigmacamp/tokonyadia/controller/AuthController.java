package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.model.dto.request.AuthRequest;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.dto.response.LoginResponse;
import com.enigmacamp.tokonyadia.model.dto.response.RegisterResponse;
import com.enigmacamp.tokonyadia.model.entities.Admin;
import com.enigmacamp.tokonyadia.model.entities.Seller;
import com.enigmacamp.tokonyadia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIUrl.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<CommonResponse<RegisterResponse>> registration(@RequestBody AuthRequest<CustomerRequest> request) {
        RegisterResponse response = authService.customerRegistration(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully register new customer")
                .data(response)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @PostMapping("/register/admin")
    @Qualifier("admin")
    public ResponseEntity<CommonResponse<RegisterResponse>> adminRegistration(@RequestBody AuthRequest<Admin> request) {

        RegisterResponse response = authService.adminRegistration(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully register new Admin")
                .data(response)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @PostMapping("/register/seller")
    @Qualifier("seller")
    public ResponseEntity<CommonResponse<RegisterResponse>> sellerRegistration(@RequestBody AuthRequest<Seller> request) {

        RegisterResponse response = authService.sellerRegistration(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully register new Seller")
                .data(response)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody AuthRequest<String> request) {
        LoginResponse response = authService.login(request);

        CommonResponse<LoginResponse> commonResponse = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully login")
                .data(response)
                .build();

        return ResponseEntity.ok(commonResponse);
    }
}