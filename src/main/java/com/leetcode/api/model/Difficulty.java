package com.leetcode.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Difficulty {

    @JsonProperty("EASY")
    EASY("EASY"),
    @JsonProperty("MEDIUM")
    MEDIUM("MEDIUM"),
    @JsonProperty("HARD")
    HARD("HARD");

    private final String difficulty;

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

}
