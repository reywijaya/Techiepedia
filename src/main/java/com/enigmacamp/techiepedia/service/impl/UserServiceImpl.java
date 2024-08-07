package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.entities.ActiveUser;
import com.enigmacamp.techiepedia.model.entities.User;
import com.enigmacamp.techiepedia.repository.UserRepository;
import com.enigmacamp.techiepedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return ActiveUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRoles().getFirst().getName())
                .build();
    }

    @Override
    public ActiveUser loadUserById(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id));
        return ActiveUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRoles().getFirst().getName())
                .build();
    }
}