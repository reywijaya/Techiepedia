package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.dto.request.AuthRequest;
import com.enigmacamp.techiepedia.model.dto.request.CustomerRequest;
import com.enigmacamp.techiepedia.model.dto.response.LoginResponse;
import com.enigmacamp.techiepedia.model.dto.response.RegisterResponse;
import com.enigmacamp.techiepedia.model.entities.*;
import com.enigmacamp.techiepedia.repository.UserRepository;
import com.enigmacamp.techiepedia.security.JwtUtil;
import com.enigmacamp.techiepedia.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("auth")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleService roleService;
    private final CustomerService customerService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AdminService adminService;
    private final SellerService sellerService;

    @Override
    public LoginResponse login(AuthRequest<String> authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ActiveUser activeUser = (ActiveUser) authentication.getPrincipal();

        String token = jwtUtil.generateToken(activeUser);

        return LoginResponse.builder()
                .token(token)
                .role(activeUser.getRole())
                .build();
    }

    @Override
    @Transactional
    public RegisterResponse customerRegistration(AuthRequest<CustomerRequest> request) {

        Role role = roleService.getOrSave(Role.ERole.CUSTOMER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder()
                .username(request.getUsername().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        user = userRepository.saveAndFlush(user);

        CustomerRequest requestData = request.getData();
        requestData.setUser(user);

        customerService.addCustomer(requestData);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .role(role.getName())
                .build();
    }

    @Override
    public RegisterResponse adminRegistration(AuthRequest<Admin> authRequest) {
        Role role = roleService.getOrSave(Role.ERole.ADMIN);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder()
                .username(authRequest.getUsername().toLowerCase())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .roles(roles)
                .build();

        user = userRepository.saveAndFlush(user);

        var admin = authRequest.getData();
        admin.setUser(user);

        adminService.createAdmin(admin);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .role(role.getName())
                .build();
    }

    @Override
    public RegisterResponse sellerRegistration(AuthRequest<Seller> authRequest) {
        Role role = roleService.getOrSave(Role.ERole.SELLER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder()
                .username(authRequest.getUsername().toLowerCase())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .roles(roles)
                .build();

        user = userRepository.saveAndFlush(user);

        var seller = authRequest.getData();
        seller.setUser(user);

        sellerService.createSeller(seller);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .role(role.getName())
                .build();
    }
}