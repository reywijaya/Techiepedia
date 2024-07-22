package com.enigmacamp.techiepedia.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    @Mock
    private AuthTokenFilter authTokenFilter;

    @Mock
    private AuthenticationConfiguration authConfig;

    @InjectMocks
    private SecurityConfiguration securityConfiguration;

    @Test
    void testSecurityFilterChainBean() throws Exception {

        HttpSecurity http = mock(HttpSecurity.class);

        when(authConfig.getAuthenticationManager()).thenReturn(mock(AuthenticationManager.class));

        SecurityFilterChain filterChain = securityConfiguration.securityFilterChain(http);

        verify(http, times(1)).httpBasic(AbstractHttpConfigurer::disable);
        verify(http, times(1)).csrf(AbstractHttpConfigurer::disable);
        verify(http, times(1)).sessionManagement(any());
        verify(http, times(1)).authorizeHttpRequests(any());
    }
}
