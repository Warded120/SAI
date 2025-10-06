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

    public boolean isFather(String f, String c) {
        return males.contains(f) && parentToChild.getOrDefault(f, List.of()).contains(c);
    }

    public boolean isMother(String m, String c) {
        return females.contains(m) && parentToChild.getOrDefault(m, List.of()).contains(c);
    }

    public boolean isSibling(String x, String y) {
        for (var entry : parentToChild.entrySet()) {
            List<String> children = entry.getValue();
            if (children.contains(x) && children.contains(y) && !x.equals(y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBrother(String b, String s) {
        return males.contains(b) && isSibling(b, s);
    }

    public boolean isSister(String s, String b) {
        return females.contains(s) && isSibling(s, b);
    }

    public boolean isGrandparent(String gp, String gc) {
        if (!parentToChild.containsKey(gp)) return false;
        for (String parent : parentToChild.get(gp)) {
            if (parentToChild.getOrDefault(parent, List.of()).contains(gc)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGrandfather(String gf, String gc) {
        return males.contains(gf) && isGrandparent(gf, gc);
    }

    public boolean isGrandmother(String gm, String gc) {
        return females.contains(gm) && isGrandparent(gm, gc);
    }

    public boolean isUncle(String u, String n) {
        if (!males.contains(u)) return false;
        for (var entry : parentToChild.entrySet()) {
            String parent = entry.getKey();
            if (entry.getValue().contains(n) && isSibling(u, parent)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAunt(String a, String n) {
        if (!females.contains(a)) return false;
        for (var entry : parentToChild.entrySet()) {
            String parent = entry.getKey();
            if (entry.getValue().contains(n) && isSibling(a, parent)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAncestor(String a, String d) {
        if (parentToChild.getOrDefault(a, List.of()).contains(d)) {
            return true;
        }
        for (String child : parentToChild.getOrDefault(a, List.of())) {
            if (isAncestor(child, d)) {
                return true;
            }
        }
        return false;
    }

    // ---------- Приклад використання ----------
    public static void main(String[] args) {
        FamilyTree tree = new FamilyTree();

        // Факти
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

        // Запити
        System.out.println("Is John father of Michael? " + tree.isFather("John", "Michael"));
        System.out.println("Is Linda mother of Michael? " + tree.isMother("Linda", "Michael"));
        System.out.println("Is Michael brother of Susan? " + tree.isBrother("Michael", "Susan"));
        System.out.println("Is Susan sister of Michael? " + tree.isSister("Susan", "Michael"));
        System.out.println("Is John grandfather of David? " + tree.isGrandfather("John", "David"));
        System.out.println("Is Linda grandmother of David? " + tree.isGrandmother("Linda", "David"));
        System.out.println("Is Susan aunt of David? " + tree.isAunt("Susan", "David"));
        System.out.println("Is John ancestor of David? " + tree.isAncestor("John", "David"));
    }
}
