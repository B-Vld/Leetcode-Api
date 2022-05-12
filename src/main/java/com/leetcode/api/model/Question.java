package com.leetcode.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
public class Question {

    private final String questionId;

    private final String title;

    private final String link;

    private final Difficulty difficulty;

    private final String acceptanceRate;

    private final List<String> topicTags;

    private final Integer likes;

    private final Integer dislikes;

    private final String date;

}