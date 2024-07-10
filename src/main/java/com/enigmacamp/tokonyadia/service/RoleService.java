package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.entities.Role;

public interface RoleService {
    Role getOrSave(Role.ERole role);
}