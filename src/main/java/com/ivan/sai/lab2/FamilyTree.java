package com.ivan.sai.lab2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FamilyTree {
    private final Set<String> males = new HashSet<>();
    private final Set<String> females = new HashSet<>();
    private final Map<String, List<String>> parentToChild = new HashMap<>();

    public void addMale(String name) {
        males.add(name);
    }

    public void addFemale(String name) {
        females.add(name);
    }

    public void addParent(String parent, String child) {
        parentToChild.computeIfAbsent(parent, k -> new ArrayList<>()).add(child);
    }

    public boolean isFather(String father, String child) {
        return males.contains(father) && parentToChild.getOrDefault(father, List.of()).contains(child);
    }

    public boolean isMother(String mother, String child) {
        return females.contains(mother) && parentToChild.getOrDefault(mother, List.of()).contains(child);
    }

    public boolean isSibling(String person1, String person2) {
        for (var entry : parentToChild.entrySet()) {
            List<String> children = entry.getValue();
            if (children.contains(person1) && children.contains(person2) && !person1.equals(person2)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBrother(String brother, String sibling) {
        return males.contains(brother) && isSibling(brother, sibling);
    }

    public boolean isSister(String sister, String sibling) {
        return females.contains(sister) && isSibling(sister, sibling);
    }

    public boolean isGrandparent(String grandparent, String grandchild) {
        if (!parentToChild.containsKey(grandparent)) return false;
        for (String parent : parentToChild.get(grandparent)) {
            if (parentToChild.getOrDefault(parent, List.of()).contains(grandchild)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGrandfather(String grandfather, String grandchild) {
        return males.contains(grandfather) && isGrandparent(grandfather, grandchild);
    }

    public boolean isGrandmother(String grandmother, String grandchild) {
        return females.contains(grandmother) && isGrandparent(grandmother, grandchild);
    }

    public boolean isUncle(String uncle, String nieceOrNephew) {
        if (!males.contains(uncle)) return false;
        for (var entry : parentToChild.entrySet()) {
            String parent = entry.getKey();
            if (entry.getValue().contains(nieceOrNephew) && isSibling(uncle, parent)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAunt(String aunt, String nieceOrNephew) {
        if (!females.contains(aunt)) return false;
        for (var entry : parentToChild.entrySet()) {
            String parent = entry.getKey();
            if (entry.getValue().contains(nieceOrNephew) && isSibling(aunt, parent)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAncestor(String ancestor, String descendant) {
        if (parentToChild.getOrDefault(ancestor, List.of()).contains(descendant)) {
            return true;
        }
        for (String child : parentToChild.getOrDefault(ancestor, List.of())) {
            if (isAncestor(child, descendant)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        FamilyTree tree = new FamilyTree();

        tree.addMale("John");
        tree.addMale("Michael");
        tree.addMale("David");

        tree.addFemale("Linda");
        tree.addFemale("Susan");

        tree.addParent("John", "Michael");
        tree.addParent("Linda", "Michael");
        tree.addParent("John", "Susan");
        tree.addParent("Linda", "Susan");
        tree.addParent("Michael", "David");

        System.out.println("Is John father of Michael? " + tree.isFather("John", "Michael"));
        System.out.println("Is Linda mother of Michael? " + tree.isMother("Linda", "Michael"));
        System.out.println("Is Michael brother of Susan? " + tree.isBrother("Michael", "Susan"));
        System.out.println("Is Susan sister of Michael? " + tree.isSister("Susan", "Michael"));
        System.out.println("Is John grandfather of David? " + tree.isGrandfather("John", "David"));
        System.out.println("Is Linda grandmother of David? " + tree.isGrandmother("Linda", "David"));
        System.out.println("Is Susan aunt of David? " + tree.isAunt("Susan", "David"));
        System.out.println("Is John ancestor of David? " + tree.isAncestor("John", "David"));
        System.out.println("Is Michael father of John? " + tree.isFather("Michael", "John")); // false
        System.out.println("Is David brother of Susan? " + tree.isBrother("David", "Susan")); // false
    }
}
