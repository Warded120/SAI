package com.ivan.sai.lab4;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Проста реалізація семантичної мережі як зберігання вузлів і ребер.
 */
public class SemanticNetwork {
    private final Map<String, Node> nodes = new HashMap<>();
    private final List<Edge> edges = new ArrayList<>();

    // Додавання/отримання вузла
    public Node createNode(String id, String type) {
        return nodes.computeIfAbsent(id, k -> new Node(id, type));
    }

    public Optional<Node> getNode(String id) { return Optional.ofNullable(nodes.get(id)); }

    public void addEdge(Node subject, String relation, Node object) {
        Edge e = new Edge(subject, relation, object);
        if (!edges.contains(e)) edges.add(e);
    }

    public List<Edge> getEdgesByRelation(String relation) {
        return edges.stream().filter(e -> e.getRelation().equalsIgnoreCase(relation)).collect(Collectors.toList());
    }

    public List<Edge> findEdges(String subjectId, String relation, String objectId) {
        return edges.stream()
                .filter(e -> (subjectId == null || e.getSubject().getId().equals(subjectId)) &&
                             (relation == null || e.getRelation().equalsIgnoreCase(relation)) &&
                             (objectId == null || e.getObject().getId().equals(objectId)))
                .collect(Collectors.toList());
    }

    // запит: contains(room, ?furniture)
    public List<Node> getContainedFurniture(String roomId) {
        return findEdges(roomId, "contains", null).stream().map(Edge::getObject).collect(Collectors.toList());
    }

    // загальний пошук вузлів по типу
    public List<Node> getNodesByType(String type) {
        return nodes.values().stream().filter(n -> n.getType().equalsIgnoreCase(type)).collect(Collectors.toList());
    }

    // простий in-memory dump
    public void printAll() {
        System.out.println("Nodes:");
        nodes.values().forEach(n -> System.out.println("  " + n + " attrs=" + n.getAttributes()));
        System.out.println("Edges:");
        edges.forEach(e -> System.out.println("  " + e));
    }
}
