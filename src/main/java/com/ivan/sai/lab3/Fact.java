package com.ivan.sai.lab3;

import lombok.Data;

@Data
public class Fact {
    public final String key;
    public final String value;
    public final double certainty;

    public Fact(String key, String value, double certainty) {
        this.key = key;
        this.value = value;
        this.certainty = Math.max(0.0, Math.min(1.0, certainty));
    }
}