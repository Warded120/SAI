package com.ivan.sai.lab3;

import lombok.Data;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Data
public class Rule {
    public final String name;
    public final double confidence;
    public final Predicate<Set<Fact>> condition;
    public final Consumer<KnowledgeBase> action;

    public Rule(String name,
                Predicate<Set<Fact>> condition,
                java.util.function.Consumer<KnowledgeBase> action,
                double confidence) {
        this.name = name;
        this.condition = condition;
        this.action = action;
        this.confidence = Math.max(0.0, Math.min(1.0, confidence));
    }
}