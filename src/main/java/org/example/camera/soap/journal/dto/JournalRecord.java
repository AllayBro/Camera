package org.example.camera.soap.journal.dto;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class JournalRecord {

    @XmlElement(required = true)
    private String droneId;

    @XmlElement(required = true)
    private String targetId;

    @XmlElement(required = true)
    private String dateTime;

    @XmlElement(required = true)
    private double latitude;

    @XmlElement(required = true)
    private double longitude;

    @XmlElement(required = true)
    private double altitude;

    @XmlElement(required = true)
    private String filePath;

    public JournalRecord() {}

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
