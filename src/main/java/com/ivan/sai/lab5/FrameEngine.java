package com.ivan.sai.lab5;

import java.util.List;
import java.util.stream.Collectors;

public class FrameEngine {

    // Пошук виходів будівлі
    public List<Exit> getExits(Building building) {
        return building.getExits();
    }

    // Пошук призначеного виходу для особи
    public Exit getAssignedExit(Person p) {
        return p.getAssignedExit();
    }

    // Пошук осіб за рухливістю
    public List<Person> getPersonsByMobility(List<Person> persons, String mobility) {
        return persons.stream().filter(p -> p.getMobility().equalsIgnoreCase(mobility)).collect(Collectors.toList());
    }

    // Пошук команд за відповідальністю
    public List<EvacuationTeam> getTeamsByResponsibility(List<EvacuationTeam> teams, String responsibility) {
        return teams.stream().filter(t -> t.getResponsibility().equalsIgnoreCase(responsibility)).collect(Collectors.toList());
    }
}
