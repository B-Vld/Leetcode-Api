package com.leetcode.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.List;

@Getter
public class DailyChallengeRepresentation {

    @JsonFormat
    private final String title;

    @JsonFormat
    private final String link;

    @JsonFormat
    private final Difficulty difficulty;

    @JsonFormat
    private final String acceptanceRate;

    @JsonFormat
    private final List<String> topicTags;

    @JsonFormat
    private final String date;

    public DailyChallengeRepresentation(String title, String link, Difficulty difficulty, String acceptanceRate, List<String> topicTags, String date) {
        this.title = title;
        this.link = link;
        this.difficulty = difficulty;
        this.acceptanceRate = acceptanceRate;
        this.topicTags = topicTags;
        this.date = date;
    }
}
