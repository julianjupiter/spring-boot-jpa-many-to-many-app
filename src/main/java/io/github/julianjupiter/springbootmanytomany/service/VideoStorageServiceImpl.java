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

@Service("videoStorageService")
public class VideoStorageServiceImpl implements StorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoStorageServiceImpl.class);
    private final Path gameVideosLocation;

    @Autowired
    public VideoStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.gameVideosLocation = Paths.get(fileStorageProperties.getUploadGameVideosDir());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(gameVideosLocation);
        } catch (IOException exception) {
            throw new StorageException("Could not initialize storage " + gameVideosLocation.toString(), exception);
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
                if (mimeType != null && mimeType.startsWith("video")) {
                    Files.copy(inputStream, this.gameVideosLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new IOException();
                }
            }
        } catch (IOException exception) {
            throw new StorageException("Failed to store file " + filename, exception);
        }
    }

    @Override
    public Path load(String filename) {
        return this.gameVideosLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException exception) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, exception);
        }
    }

}
