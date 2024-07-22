package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.entities.ActiveUser;
import com.enigmacamp.techiepedia.model.entities.Role;
import com.enigmacamp.techiepedia.model.entities.User;
import com.enigmacamp.techiepedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .id("1")
                .username("user1")
                .password("password")
                .roles(Collections.singletonList(Role.builder().name(Role.ERole.CUSTOMER).build()))
                .build();
    }

    @Test
    void loadUserByUsername_existingUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        ActiveUser activeUser = (ActiveUser) userService.loadUserByUsername(user.getUsername());

        assertEquals(user.getId(), activeUser.getId());
        assertEquals(user.getUsername(), activeUser.getUsername());
        assertEquals(user.getPassword(), activeUser.getPassword());
        assertEquals(user.getRoles().getFirst().getName(), activeUser.getRole());

        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void loadUserByUsername_userNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("non existing user"));

        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void loadUserById_existingUser() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        ActiveUser activeUser = userService.loadUserById(user.getId());

        assertEquals(user.getId(), activeUser.getId());
        assertEquals(user.getUsername(), activeUser.getUsername());
        assertEquals(user.getPassword(), activeUser.getPassword());
        assertEquals(user.getRoles().getFirst().getName(), activeUser.getRole());

        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void loadUserById_userNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserById("non existing id"));

        verify(userRepository, times(1)).findById(anyString());
    }
}