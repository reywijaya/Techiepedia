package com.enigmacamp.techiepedia.service;

import com.enigmacamp.techiepedia.model.entities.Role;

public interface RoleService {
    Role getOrSave(Role.ERole role);
}