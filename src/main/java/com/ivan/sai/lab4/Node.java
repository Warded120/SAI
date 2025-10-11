package com.ivan.sai.lab4;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Вузол семантичної мережі.
 * type - категорія (Room, Furniture, Material, Function, Style)
 * id - унікальний ідентифікатор/назва
 * attributes - довільні атрибути
 */
@Data
public class Node {
    private final String id;
    private final String type;
    private final Map<String, String> attributes = new HashMap<>();

    public Node(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public void setAttribute(String key, String value) { attributes.put(key, value); }
    public String getAttribute(String key) { return attributes.get(key); }
}
