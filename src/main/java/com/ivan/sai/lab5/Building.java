package com.ivan.sai.lab5;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private String name;
    private int floors;
    private List<Exit> exits = new ArrayList<>();

    public Building(String name, int floors) {
        this.name = name;
        this.floors = floors;
    }

    public String getName() { return name; }
    public int getFloors() { return floors; }
    public List<Exit> getExits() { return exits; }

    public void addExit(Exit exit) { exits.add(exit); }
}
