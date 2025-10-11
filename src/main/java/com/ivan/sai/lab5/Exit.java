package com.ivan.sai.lab5;

public class Exit {
    private String location;
    private String type;
    private int capacity;

    public Exit(String location, String type, int capacity) {
        this.location = location;
        this.type = type;
        this.capacity = capacity;
    }

    public String getLocation() { return location; }
    public String getType() { return type; }
    public int getCapacity() { return capacity; }
}
