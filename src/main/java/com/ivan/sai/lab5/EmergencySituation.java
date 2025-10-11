package com.ivan.sai.lab5;

import java.util.ArrayList;
import java.util.List;

public class EmergencySituation {
    private String type; // пожежа, землетрус
    private String severity;
    private List<String> affectedAreas = new ArrayList<>();

    public EmergencySituation(String type, String severity) {
        this.type = type;
        this.severity = severity;
    }

    public String getType() { return type; }
    public String getSeverity() { return severity; }
    public List<String> getAffectedAreas() { return affectedAreas; }

    public void addAffectedArea(String area) { affectedAreas.add(area); }
}
