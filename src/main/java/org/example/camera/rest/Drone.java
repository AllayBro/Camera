package org.example.camera.rest;

public class Drone {
    private String id;
    private String model;
    private String status;

    public Drone() {}
    public Drone(String id, String model, String status) {
        this.id = id;
        this.model = model;
        this.status = status;
    }

    public String getId() { return id; }
    public String getModel() { return model; }
    public String getStatus() { return status; }

    public void setId(String id) { this.id = id; }
    public void setModel(String model) { this.model = model; }
    public void setStatus(String status) { this.status = status; }
}
