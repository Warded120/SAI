package com.ivan.sai.lab5;

import java.util.ArrayList;
import java.util.List;

public class EvacuationPlan {
    private Building building;
    private List<Exit> primaryExits = new ArrayList<>();
    private List<Exit> alternativeExits = new ArrayList<>();
    private List<EvacuationTeam> assignedTeams = new ArrayList<>();

    public EvacuationPlan(Building building) {
        this.building = building;
    }

    public Building getBuilding() { return building; }
    public List<Exit> getPrimaryExits() { return primaryExits; }
    public List<Exit> getAlternativeExits() { return alternativeExits; }
    public List<EvacuationTeam> getAssignedTeams() { return assignedTeams; }

    public void addPrimaryExit(Exit e) { primaryExits.add(e); }
    public void addAlternativeExit(Exit e) { alternativeExits.add(e); }
    public void addAssignedTeam(EvacuationTeam t) { assignedTeams.add(t); }
}
