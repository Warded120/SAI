package com.ivan.sai.lab5;

import java.util.ArrayList;
import java.util.List;

public class EvacuationTeam {
    private String name;
    private List<Person> members = new ArrayList<>();
    private String responsibility;

    public EvacuationTeam(String name, String responsibility) {
        this.name = name;
        this.responsibility = responsibility;
    }

    public String getName() { return name; }
    public List<Person> getMembers() { return members; }
    public String getResponsibility() { return responsibility; }

    public void addMember(Person p) { members.add(p); }
}
