package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.model.entities.Admin;
import com.enigmacamp.techiepedia.repository.AdminRepository;
import com.enigmacamp.techiepedia.service.AdminService;
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