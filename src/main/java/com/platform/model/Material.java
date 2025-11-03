package com.platform.model;

import java.sql.Timestamp;

public class Material {
    private int materialId;
    private String title;
    private String description;
    private String fileName;
    private String filePath;
    private Timestamp uploadDate;
    private int uploaderId;
    private String uploaderUsername; // Added for display

    // Getters and Setters
    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public Timestamp getUploadDate() { return uploadDate; }
    public void setUploadDate(Timestamp uploadDate) { this.uploadDate = uploadDate; }
    public int getUploaderId() { return uploaderId; }
    public void setUploaderId(int uploaderId) { this.uploaderId = uploaderId; }
    public String getUploaderUsername() { return uploaderUsername; }
    public void setUploaderUsername(String uploaderUsername) { this.uploaderUsername = uploaderUsername; }
}