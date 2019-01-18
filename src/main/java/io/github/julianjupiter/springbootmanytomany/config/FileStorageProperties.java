package io.github.julianjupiter.springbootmanytomany.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

    private String uploadGameScreenshotsDir;

    private String uploadGameVideosDir;

    public String getUploadGameScreenshotsDir() {
        return uploadGameScreenshotsDir;
    }

    public void setUploadGameScreenshotsDir(String uploadGameScreenshotsDir) {
        this.uploadGameScreenshotsDir = uploadGameScreenshotsDir;
    }

    public String getUploadGameVideosDir() {
        return uploadGameVideosDir;
    }

    public void setUploadGameVideosDir(String uploadGameVideosDir) {
        this.uploadGameVideosDir = uploadGameVideosDir;
    }
}