package com.ivan.sai.lab5;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Створення об'єктів
        Building building = new Building("Office", 3);
        Exit mainExit = new Exit("Front Door", "Main", 50);
        Exit sideExit = new Exit("Side Door", "Emergency", 20);
        building.addExit(mainExit);
        building.addExit(sideExit);

        Person alice = new Person("Alice", "normal");
        Person bob = new Person("Bob", "disabled");
        alice.setAssignedExit(mainExit);
        bob.setAssignedExit(sideExit);

        EvacuationTeam team1 = new EvacuationTeam("Team A", "Guide");
        team1.addMember(alice);
        team1.addMember(bob);

        EvacuationPlan plan = new EvacuationPlan(building);
        plan.addPrimaryExit(mainExit);
        plan.addAlternativeExit(sideExit);
        plan.addAssignedTeam(team1);

        FrameEngine engine = new FrameEngine();

        // Приклади запитів
        System.out.println("1) Виходи будівлі:");
        engine.getExits(building).forEach(e -> System.out.println(" - " + e.getLocation()));

        System.out.println("\n2) Призначений вихід для Bob:");
        System.out.println(" - " + engine.getAssignedExit(bob).getLocation());

        System.out.println("\n3) Особи з обмеженою рухливістю:");
        List<Person> persons = Arrays.asList(alice, bob);
        engine.getPersonsByMobility(persons, "disabled").forEach(p -> System.out.println(" - " + p.getName()));

        System.out.println("\n4) Команди, відповідальні за 'Guide':");
        List<EvacuationTeam> teams = Arrays.asList(team1);
        engine.getTeamsByResponsibility(teams, "Guide").forEach(t -> System.out.println(" - " + t.getName()));
    }
}
