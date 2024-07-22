package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.entities.Role;
import com.enigmacamp.techiepedia.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = Role.builder()
                .name(Role.ERole.CUSTOMER)
                .build();
    }

    @Test
    void getOrSave_RoleExists() {
        when(roleRepository.findByName(Role.ERole.CUSTOMER)).thenReturn(Optional.of(role));

        Role foundRole = roleService.getOrSave(Role.ERole.CUSTOMER);

        assertNotNull(foundRole);
        assertEquals(role.getName(), foundRole.getName());
        verify(roleRepository, times(1)).findByName(Role.ERole.CUSTOMER);
        verify(roleRepository, times(0)).saveAndFlush(any(Role.class));
    }

    @Test
    void getOrSave_RoleDoesNotExist() {
        when(roleRepository.findByName(Role.ERole.CUSTOMER)).thenReturn(Optional.empty());
        when(roleRepository.saveAndFlush(any(Role.class))).thenReturn(role);

        Role savedRole = roleService.getOrSave(Role.ERole.CUSTOMER);

        assertNotNull(savedRole);
        assertEquals(role.getName(), savedRole.getName());
        verify(roleRepository, times(1)).findByName(Role.ERole.CUSTOMER);
        verify(roleRepository, times(1)).saveAndFlush(any(Role.class));
    }
}