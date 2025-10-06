package com.ivan.sai.lab3;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
public class KnowledgeBase {
        private final Set<Fact> facts = new LinkedHashSet<>();
        private final List<Rule> rules = new ArrayList<>();

        public void addFact(Fact f) {
            // Якщо факт вже присутній з меншою достовірністю — замінюємо
            Optional<Fact> existing = facts.stream()
                    .filter(x -> x.key.equals(f.key) && x.value.equals(f.value))
                    .findFirst();
            if (existing.isPresent()) {
                Fact ex = existing.get();
                if (f.certainty > ex.certainty) {
                    facts.remove(ex);
                    facts.add(f);
                }
            } else {
                facts.add(f);
            }
        }

        public void addFact(String key, String value, double certainty) {
            addFact(new Fact(key, value, certainty));
        }

        public void addRule(Rule r) {
            rules.add(r);
        }
}