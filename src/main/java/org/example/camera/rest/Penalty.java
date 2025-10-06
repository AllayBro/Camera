package org.example.camera.rest;

public class Penalty {
    private String id;
    private String droneId;
    private String targetId;
    private String dateTime;
    private String filePath;
    private PhotoMetadata metadata;

    public Penalty() {}

    public Penalty(String id, String droneId, String targetId, String dateTime, String filePath, PhotoMetadata metadata) {
        this.id = id;
        this.droneId = droneId;
        this.targetId = targetId;
        this.dateTime = dateTime;
        this.filePath = filePath;
        this.metadata = metadata;
    }

    public String getId() { return id; }
    public String getDroneId() { return droneId; }
    public String getTargetId() { return targetId; }
    public String getDateTime() { return dateTime; }
    public String getFilePath() { return filePath; }
    public PhotoMetadata getMetadata() { return metadata; }

    public void setId(String id) { this.id = id; }
    public void setDroneId(String droneId) { this.droneId = droneId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setMetadata(PhotoMetadata metadata) { this.metadata = metadata; }
}
