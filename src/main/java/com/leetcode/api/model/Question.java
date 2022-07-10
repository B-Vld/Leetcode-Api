package com.leetcode.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@ToString
public class Question {

    private final String questionId;

    private final String title;

    private final String submitUrl;

    private final Difficulty difficulty;

    private final String acRate;

    private final List<String> topicTags;

    private final Integer likes;

    private final Integer dislikes;

}
