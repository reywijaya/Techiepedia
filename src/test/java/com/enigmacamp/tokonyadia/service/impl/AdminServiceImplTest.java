package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.entities.Admin;
import com.enigmacamp.tokonyadia.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = Admin.builder()
                .id("admin1")
                .name("Admin Name")
                .build();
    }

    @Test
    void createAdmin() {
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin savedAdmin = adminService.createAdmin(admin);

        assertNotNull(savedAdmin);
        assertNotNull(savedAdmin.getId());
        assertNotNull(savedAdmin.getName());
        verify(adminRepository, times(1)).save(any(Admin.class));
    }
}