package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.dto.request.AuthRequest;
import com.enigmacamp.techiepedia.model.dto.request.CustomerRequest;
import com.enigmacamp.techiepedia.model.dto.response.LoginResponse;
import com.enigmacamp.techiepedia.model.dto.response.RegisterResponse;
import com.enigmacamp.techiepedia.model.entities.*;
import com.enigmacamp.techiepedia.repository.UserRepository;
import com.enigmacamp.techiepedia.security.JwtUtil;
import com.enigmacamp.techiepedia.service.AdminService;
import com.enigmacamp.techiepedia.service.CustomerService;
import com.enigmacamp.techiepedia.service.RoleService;
import com.enigmacamp.techiepedia.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private RoleService roleService;

    @Mock
    private CustomerService customerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AdminService adminService;

    @Mock
    private SellerService sellerService;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private AuthRequest<CustomerRequest> customerAuthRequest;
    private Role customerRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customerRole = Role.builder().name(Role.ERole.CUSTOMER).build();
        List<Role> roles = new ArrayList<>();
        roles.add(customerRole);

        user = User.builder()
                .id("1")
                .username("johny")
                .password("depp")
                .roles(roles)
                .build();

        CustomerRequest customerRequest = CustomerRequest.builder()
                .id("1")
                .user(user)
                .address("texas")
                .email("johny@mail.com")
                .phone("8976")
                .name("johny")
                .birthDate(new Date())
                .build();

        customerAuthRequest = AuthRequest.<CustomerRequest>builder()
                .username("johny")
                .password("depp")
                .data(customerRequest)
                .build();
    }

    @Test
    void login() {
        Authentication authentication = mock(Authentication.class);
        ActiveUser activeUser = mock(ActiveUser.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(activeUser);
        when(jwtUtil.generateToken(activeUser)).thenReturn("token");

        LoginResponse response = authService.login(AuthRequest.<String>builder().username("johny").password("depp").build());

        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, times(1)).generateToken(activeUser);
    }

    @Test
    void customerRegistration() {
        when(roleService.getOrSave(Role.ERole.CUSTOMER)).thenReturn(customerRole);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        RegisterResponse response = authService.customerRegistration(customerAuthRequest);

        assertNotNull(response);
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(customerRole.getName(), response.getRole());
        verify(roleService, times(1)).getOrSave(Role.ERole.CUSTOMER);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).saveAndFlush(any(User.class));
        verify(customerService, times(1)).addCustomer(any(CustomerRequest.class));
    }

    @Test
    void adminRegistration() {
        Role adminRole = Role.builder().name(Role.ERole.ADMIN).build();
        Admin admin = new Admin();
        AuthRequest<Admin> authRequest = AuthRequest.<Admin>builder()
                .username("admin")
                .password("password")
                .data(admin)
                .build();

        when(roleService.getOrSave(Role.ERole.ADMIN)).thenReturn(adminRole);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        RegisterResponse response = authService.adminRegistration(authRequest);

        assertNotNull(response);
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(adminRole.getName(), response.getRole());
        verify(roleService, times(1)).getOrSave(Role.ERole.ADMIN);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).saveAndFlush(any(User.class));
        verify(adminService, times(1)).createAdmin(any(Admin.class));
    }

    @Test
    void sellerRegistration() {
        Role sellerRole = Role.builder().name(Role.ERole.SELLER).build();
        Seller seller = new Seller();
        AuthRequest<Seller> authRequest = AuthRequest.<Seller>builder()
                .username("seller")
                .password("password")
                .data(seller)
                .build();

        when(roleService.getOrSave(Role.ERole.SELLER)).thenReturn(sellerRole);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        RegisterResponse response = authService.sellerRegistration(authRequest);

        assertNotNull(response);
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(sellerRole.getName(), response.getRole());
        verify(roleService, times(1)).getOrSave(Role.ERole.SELLER);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).saveAndFlush(any(User.class));
        verify(sellerService, times(1)).createSeller(any(Seller.class));
    }
}