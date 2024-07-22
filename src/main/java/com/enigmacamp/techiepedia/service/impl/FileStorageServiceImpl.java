package com.enigmacamp.techiepedia.service.impl;

import com.enigmacamp.techiepedia.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
    public String storeFile(MultipartFile file, String id) {

        String fileName = id + "_" + Objects.requireNonNull(file.getOriginalFilename());
        try {
            Path target = rootLocation.resolve(fileName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to store file {}", file.getOriginalFilename(), e);
        }
        return fileName;
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path file = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("Could not read file: {}", fileName);
            }
        } catch (IOException e) {
            log.error("Could not read file: {}", fileName, e);
        }
        return null;
    }
}