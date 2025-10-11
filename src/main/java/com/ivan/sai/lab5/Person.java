package com.ivan.sai.lab5;

public class Person {
    private String name;
    private String mobility; // normal, disabled
    private Exit assignedExit;

    public Person(String name, String mobility) {
        this.name = name;
        this.mobility = mobility;
    }

    public String getName() { return name; }
    public String getMobility() { return mobility; }
    public Exit getAssignedExit() { return assignedExit; }
    public void setAssignedExit(Exit exit) { this.assignedExit = exit; }
}
