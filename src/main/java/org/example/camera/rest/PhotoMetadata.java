package org.example.camera.rest;

public class PhotoMetadata {
    private String resolution;
    private String fileSize;
    private String format;

    public PhotoMetadata() {}

    public PhotoMetadata(String resolution, String fileSize, String format) {
        this.resolution = resolution;
        this.fileSize = fileSize;
        this.format = format;
    }

    public String getResolution() { return resolution; }
    public String getFileSize() { return fileSize; }
    public String getFormat() { return format; }

    public void setResolution(String resolution) { this.resolution = resolution; }
    public void setFileSize(String fileSize) { this.fileSize = fileSize; }
    public void setFormat(String format) { this.format = format; }
}
