package com.ivan.sai.lab3;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class WeatherExpertSystem {
    public static void main(String[] args) {
        KnowledgeBase kb = new KnowledgeBase();

//        kb.addFact("temperature", "18.5", 0.95);
//        kb.addFact("humidity", "high", 0.9);
//        kb.addFact("cloudCover", "high", 0.8);
//        kb.addFact("pressureTrend", "falling", 0.7);
//        kb.addFact("windSpeed", "10.0", 0.8);
//        kb.addFact("timeOfDay", "day", 1.0);

        kb.addFact("temperature", "35.0", 0.95);
        kb.addFact("humidity", "low", 0.8);
        kb.addFact("cloudCover", "low", 0.6);
        kb.addFact("pressureTrend", "stable", 0.7);
        kb.addFact("windSpeed", "5.0", 0.8);
        kb.addFact("timeOfDay", "day", 1.0);


        populateKnowledgeBase(kb);

        System.out.println("Provided facts:");
        System.out.println(kb);

        InferenceEngine engine = new InferenceEngine(kb);
        engine.run();

        System.out.println("\nResult facts after inference:");
        kb.getFacts().forEach(System.out::println);

        System.out.println("\nForecast");
        kb.getFacts().stream()
                .filter(f -> f.key.equals("forecast") || f.key.equals("hazard"))
                .forEach(f -> System.out.println(f.key + ": " + f.value + " (conf=" + f.certainty + ")"));
    }

    public static Predicate<Set<Fact>> hasFact(String key, String value) {
        return facts -> facts.stream().anyMatch(f -> f.key.equals(key) && f.value.equals(value));
    }

    public static Predicate<Set<Fact>> numericFactGreaterThan(String key, double threshold) {
        return facts -> facts.stream()
                .filter(f -> f.key.equals(key))
                .map(f -> tryParseDouble(f.value))
                .filter(Objects::nonNull)
                .anyMatch(v -> v > threshold);
    }

    public static Predicate<Set<Fact>> numericFactLessThan(String key, double threshold) {
        return facts -> facts.stream()
                .filter(f -> f.key.equals(key))
                .map(f -> tryParseDouble(f.value))
                .filter(Objects::nonNull)
                .anyMatch(v -> v < threshold);
    }

    private static Double tryParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static void populateKnowledgeBase(KnowledgeBase kb) {
        // Rule 1: Якщо висока вологість, тиск падає і велика хмарність -> ймовірний дощ
        kb.addRule(new Rule(
                "RainPrediction",
                facts -> hasFact("humidity", "high").test(facts)
                        && hasFact("cloudCover", "high").test(facts)
                        && hasFact("pressureTrend", "falling").test(facts),
                k -> k.addFact("forecast", "rain", 0.8),
                0.8
        ));

        // Rule 2: Якщо температура висока та вологість низька -> спека
        kb.addRule(new Rule(
                "HeatwavePrediction",
                facts -> numericFactGreaterThan("temperature", 30.0).test(facts)
                        && hasFact("humidity", "low").test(facts),
                k -> k.addFact("forecast", "heatwave", 0.7),
                0.7
        ));

        // Rule 3: Якщо температура близько до 0 та тиск стабільний і вологість висока -> ймовірний сніг
        kb.addRule(new Rule(
                "SnowPrediction",
                facts -> numericFactLessThan("temperature", 2.0).test(facts)
                        && hasFact("humidity", "high").test(facts)
                        && hasFact("cloudCover", "high").test(facts),
                k -> k.addFact("forecast", "snow", 0.75),
                0.75
        ));

        // Rule 4: Якщо виявлено дощ і сильний вітер -> можливі штормові умови
        kb.addRule(new Rule(
                "StormPrediction",
                facts -> hasFact("forecast", "rain").test(facts)
                        && numericFactGreaterThan("windSpeed", 15.0).test(facts),
                k -> k.addFact("hazard", "storm", 0.9),
                0.9
        ));

        // Rule 5: Якщо немає ознак опадів та хмарність низька -> ясно
        kb.addRule(new Rule(
                "ClearSkyPrediction",
                facts -> hasFact("cloudCover", "low").test(facts)
                        && !hasFact("forecast", "rain").test(facts)
                        && !hasFact("forecast", "snow").test(facts),
                k -> k.addFact("forecast", "clear", 0.85),
                0.85
        ));

        // Rule 6: Якщо прохолодно вночі і вологість висока -> роси або заморозки
        kb.addRule(new Rule(
                "FrostOrDew",
                facts -> numericFactLessThan("temperature", 4.0).test(facts)
                        && hasFact("timeOfDay", "night").test(facts)
                        && hasFact("humidity", "high").test(facts),
                k -> k.addFact("forecast", "frost_or_dew", 0.6),
                0.6
        ));
    }
}
