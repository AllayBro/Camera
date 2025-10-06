package org.example.camera.rest;

public class Target {
    private String id;
    private String name;
    private String type;
    private Coordinates coordinates;

    public Target() {}

    public Target(String id, String name, String type, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public Coordinates getCoordinates() { return coordinates; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
}