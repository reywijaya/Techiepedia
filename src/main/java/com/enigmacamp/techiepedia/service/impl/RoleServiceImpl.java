package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.entities.Role;
import com.enigmacamp.techiepedia.repository.RoleRepository;
import com.enigmacamp.techiepedia.service.RoleService;
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
