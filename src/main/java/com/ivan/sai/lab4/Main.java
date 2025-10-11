package com.ivan.sai.lab4;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SemanticNetwork net = new SemanticNetwork();

        // Створюємо вузли
        Node bedroom = net.createNode("Bedroom", "Room");
        Node living = net.createNode("LivingRoom", "Room");

        Node bed = net.createNode("Bed", "Furniture");
        bed.setAttribute("size", "180x200");
        Node wardrobe = net.createNode("Wardrobe", "Furniture");
        Node sofa = net.createNode("Sofa", "Furniture");
        Node coffeeTable = net.createNode("CoffeeTable", "Furniture");
        Node chair = net.createNode("Chair", "Furniture");

        Node wood = net.createNode("Wood", "Material");
        Node metal = net.createNode("Metal", "Material");

        Node sleeping = net.createNode("Sleeping", "Function");
        Node seating = net.createNode("Seating", "Function");
        Node storage = net.createNode("Storage", "Function");

        // Ребра
        net.addEdge(bedroom, "contains", bed);
        net.addEdge(bedroom, "contains", wardrobe);
        net.addEdge(living, "contains", sofa);
        net.addEdge(living, "contains", coffeeTable);
        net.addEdge(living, "contains", chair);

        net.addEdge(bed, "hasFunction", sleeping);
        net.addEdge(wardrobe, "hasFunction", storage);
        net.addEdge(sofa, "hasFunction", seating);
        net.addEdge(chair, "hasFunction", seating);
        net.addEdge(coffeeTable, "hasFunction", "Surface".equals("Surface") ? net.createNode("Surface", "Function") : null); // quick demo

        net.addEdge(bed, "madeOf", wood);
        net.addEdge(wardrobe, "madeOf", wood);
        net.addEdge(sofa, "madeOf", metal); // імпровізовано

        net.addEdge(sofa, "suitableFor", living);
        net.addEdge(coffeeTable, "suitableFor", living);
        net.addEdge(bed, "suitableFor", bedroom);

        // сумісність
        net.addEdge(sofa, "compatibleWith", coffeeTable);

        // Друк мережі
        net.printAll();

        // Інференс-движок
        InferenceEngine engine = new InferenceEngine(net);

        // Приклади запитів:
        System.out.println("\n1) Меблі у Bedroom:");
        List<Node> furnitureInBedroom = engine.getFurnitureInRoom("Bedroom");
        furnitureInBedroom.forEach(n -> System.out.println(" - " + n.getId()));

        System.out.println("\n2) Меблі з функцією 'Seating':");
        engine.getFurnitureByFunction("Seating").forEach(n -> System.out.println(" - " + n.getId()));

        System.out.println("\n3) Що рекомендується для 'Seating' у LivingRoom:");
        engine.recommendForRoomByFunction("LivingRoom", "Seating").forEach(n -> System.out.println(" - " + n.getId()));

        System.out.println("\n4) Які функції підтримує Bedroom (виведення):");
        engine.roomSupportsFunction("Bedroom").forEach(f -> System.out.println(" - " + f));
    }
}
