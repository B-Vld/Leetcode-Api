package com.leetcode.api.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.leetcode.api.exception.DifficultyException;

import java.util.Locale;

public enum Difficulty {

    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD");

    private final String value;

    Difficulty(String difficulty) {
        this.value = difficulty;
    }

    public static Difficulty of(String str) {
        if (null == str || str.isEmpty()) {
            return null;
        } else {
            var strUpper = str.toUpperCase(Locale.ROOT);
            for (var item : Difficulty.values()) {
                if (strUpper.equals(item.getValue())) {
                    return item;
                }
            }
        }
        throw new DifficultyException(String.format("%s is not a difficulty option.", str));
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
