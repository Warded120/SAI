package com.ivan.sai.lab4;

import lombok.Data;

/**
 * Орієнтоване ребро: subject -[relation]-> object
 */
@Data
public class Edge {
    private final Node subject;
    private final String relation;
    private final Node object;

    public Edge(Node subject, String relation, Node object) {
        this.subject = subject;
        this.relation = relation;
        this.object = object;
    }
}
