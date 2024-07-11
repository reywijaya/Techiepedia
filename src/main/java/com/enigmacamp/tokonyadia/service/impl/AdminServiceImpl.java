package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.entities.Admin;
import com.enigmacamp.tokonyadia.repository.AdminRepository;
import com.enigmacamp.tokonyadia.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
}