package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    @Override
    public String storeFile(MultipartFile file) {
        try {
            Path target = Path.of("assets/images/" + file.getOriginalFilename());
            Files.copy(file.getInputStream(), target);
        } catch (IOException e) {
            log.info("Failed to store file {}", file.getOriginalFilename(), e);
        }
        return "Successfully stored file " + file.getOriginalFilename();
    }
}