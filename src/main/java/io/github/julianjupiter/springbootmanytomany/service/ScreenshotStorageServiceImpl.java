package io.github.julianjupiter.springbootmanytomany.service;

import io.github.julianjupiter.springbootmanytomany.config.FileStorageProperties;
import io.github.julianjupiter.springbootmanytomany.exception.StorageException;
import io.github.julianjupiter.springbootmanytomany.exception.StorageFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service("screenshotStorageService")
public class ScreenshotStorageServiceImpl implements StorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScreenshotStorageServiceImpl.class);
    private final Path gameScreenshotsLocation;

    @Autowired
    public ScreenshotStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.gameScreenshotsLocation = Paths.get(fileStorageProperties.getUploadGameScreenshotsDir());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(gameScreenshotsLocation);
        } catch (IOException exception) {
            throw new StorageException("Could not initialize storage " + gameScreenshotsLocation.toString(), exception);
        }
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }

            if (filename.contains("..")) {
                throw new StorageException("Cannot store file with relative path outside current directory " + filename);
            }

            try (InputStream inputStream = file.getInputStream()) {
                String mimeType = file.getContentType();
                if (mimeType != null && mimeType.startsWith("image")) {
                    Files.copy(inputStream, this.gameScreenshotsLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new IOException();
                }
            }
        } catch (IOException exception) {
            throw new StorageException("Failed to store file " + filename, exception);
        }
    }

    @Override
    public Path load(String fileName) {

        return this.gameScreenshotsLocation.resolve(fileName);
    }

    @Override
    public Resource loadAsResource(String fileName) {
        try {
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + fileName);
            }
        } catch (MalformedURLException exception) {
            throw new StorageFileNotFoundException("Could not read file: " + fileName, exception);
        }
    }

}
