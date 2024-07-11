package com.enigmacamp.tokonyadia.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.enigmacamp.tokonyadia.model.dto.request.AuthRequest;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.LoginResponse;
import com.enigmacamp.tokonyadia.model.dto.response.RegisterResponse;
import com.enigmacamp.tokonyadia.model.entities.ActiveUser;
import com.enigmacamp.tokonyadia.model.entities.Role;
import com.enigmacamp.tokonyadia.model.entities.User;
import com.enigmacamp.tokonyadia.repository.UserRepository;
import com.enigmacamp.tokonyadia.service.AuthService;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.enigmacamp.tokonyadia.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public LoginResponse login(AuthRequest<String> authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ActiveUser activeUser = (ActiveUser) authentication.getPrincipal();

        String token = JWT.create().withIssuer("Tokonyadia App")
                .withSubject(activeUser.getId())
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .withIssuedAt(Instant.now())
                .withClaim("role", activeUser.getRole().name())
                .sign(Algorithm.HMAC256("secret-key-value"));

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
}
