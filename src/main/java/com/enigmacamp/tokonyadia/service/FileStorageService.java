package com.enigmacamp.tokonyadia.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file, String id);
    Resource loadFile(String fileName);
}