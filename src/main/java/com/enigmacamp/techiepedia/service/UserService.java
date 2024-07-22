package com.enigmacamp.techiepedia.service;

import com.enigmacamp.techiepedia.model.entities.ActiveUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    ActiveUser loadUserById(String id) throws UsernameNotFoundException;
}