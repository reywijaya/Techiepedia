package com.enigmacamp.tokonyadia.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void storeFile(MultipartFile file);
}
