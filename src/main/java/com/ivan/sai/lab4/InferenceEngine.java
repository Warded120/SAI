package com.ivan.sai.lab4;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Простий механізм виведення, що виконує типові запити/правила.
 * Це не повноцінна логічна машина — але ілюструє виведення на базі мережі.
 */
public class InferenceEngine {
    private final SemanticNetwork network;

    public InferenceEngine(SemanticNetwork network) {
        this.network = network;
    }

    // Запит: які меблі в кімнаті?
    public List<Node> getFurnitureInRoom(String roomId) {
        return network.getContainedFurniture(roomId);
    }

    // Запит: які предмети мають функцію (наприклад "Storage")?
    public List<Node> getFurnitureByFunction(String functionId) {
        // шукаємо ребра hasFunction
        return network.findEdges(null, "hasFunction", functionId)
                      .stream().map(e -> e.getSubject()).collect(Collectors.toList());
    }

    // Запит: знайти меблі, що мають функцію і зроблені з матеріалу
    public List<Node> getFurnitureByFunctionAndMaterial(String functionId, String materialId) {
        Set<Node> byFunction = new HashSet<>(getFurnitureByFunction(functionId));
        Set<Node> byMaterial = network.findEdges(null, "madeOf", materialId)
                                      .stream().map(e -> e.getSubject()).collect(Collectors.toSet());
        byFunction.retainAll(byMaterial);
        return new ArrayList<>(byFunction);
    }

    // Рекомендація: що поставити для 'Seating' в певній кімнаті — беремо furniture, що suitableFor room або general suitableFor type
    public List<Node> recommendForRoomByFunction(String roomId, String functionId) {
        // 1. меблі, які мають hasFunction=functionId
        List<Node> candidates = getFurnitureByFunction(functionId);

        // 2. фільтруємо за suitableFor(roomId) або suitableFor(roomType) (simplified)
        List<Node> suitable = candidates.stream().filter(f ->
            !network.findEdges(f.getId(), "suitableFor", roomId).isEmpty()
            || !network.findEdges(f.getId(), "suitableFor", "Any").isEmpty()
        ).collect(Collectors.toList());

        if (!suitable.isEmpty()) return suitable;
        // якщо нічого не знайдено — повертаємо candidates (широка рекомендація)
        return candidates;
    }

    // Просте правило: якщо A contains B і B hasFunction F => A supports F (room supports function)
    public List<String> roomSupportsFunction(String roomId) {
        List<Node> contents = getFurnitureInRoom(roomId);
        return contents.stream()
                .flatMap(n -> network.findEdges(n.getId(), "hasFunction", null).stream().map(e -> e.getObject().getId()))
                .distinct()
                .collect(Collectors.toList());
    }
}
