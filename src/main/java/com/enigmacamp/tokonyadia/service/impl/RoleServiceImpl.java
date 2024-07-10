package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.entities.Role;
import com.enigmacamp.tokonyadia.repository.RoleRepository;
import com.enigmacamp.tokonyadia.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(Role.ERole role) {
        Optional<Role> optionalRole = roleRepository.findByName(role);
        // role available return it
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }

        // role not available create new
        Role currentRole = Role.builder()
                .name(role)
                .build();

        return roleRepository.saveAndFlush(currentRole);

    }
}
