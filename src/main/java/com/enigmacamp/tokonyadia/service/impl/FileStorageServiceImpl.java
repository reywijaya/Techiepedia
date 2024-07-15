package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    private final Path rootLocation;

    public FileStorageServiceImpl() {
        this.rootLocation = Path.of("assets/images/");
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void storeFile(MultipartFile file) {
        try {
            Path target = rootLocation.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to store file {}", file.getOriginalFilename(), e);
        }
    }
}