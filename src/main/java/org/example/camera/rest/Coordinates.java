package org.example.camera.rest;

public class Coordinates {
    private double latitude;
    private double longitude;
    private double altitude;
    private String precision;

    public Coordinates() {}

    public Coordinates(double latitude, double longitude, double altitude, String precision) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.precision = precision;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getAltitude() { return altitude; }
    public String getPrecision() { return precision; }

    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void setAltitude(double altitude) { this.altitude = altitude; }
    public void setPrecision(String precision) { this.precision = precision; }
}
