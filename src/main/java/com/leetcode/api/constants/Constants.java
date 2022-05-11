package com.leetcode.api.constants;

import com.leetcode.api.model.Difficulty;
import okhttp3.MediaType;

public final class Constants {

    private Constants() {}

    public static final String LEETCODE_BASE_URL = "https://leetcode.com";
    public static final String LEETCODE_GRAPHQL_URL = "https://leetcode.com/graphql";
    public static final String LEETCODE_PROBLEMSETS = "https://leetcode.com/problemset/all/";

    public static final String JSON = "application/json";
    public static final MediaType JSON_MEDIA = MediaType.parse(JSON);

    public static final String QUERY_QUESTION_OF_TODAY = "{\"query\":\"query questionOfToday { activeDailyCodingChallengeQuestion {date link question {acRate questionId likes dislikes difficulty title topicTags { name } } } } \",\"variables\":{}}";
    public static String QUERY_RANDOM_PROBLEM(Difficulty difficulty) {
        return String.format("\"query\":" +
                "\"query randomQuestion($categorySlug: String, $filters: QuestionListFilterInput) { " +
                    "randomQuestion(categorySlug: $categorySlug, filters: $filters) { " +
                        "acRate questionId likes dislikes difficulty title topicTags { name } submitUrl } } \"" +
                ",\"variables\":{ \"categorySlug\": \"\", \"filters\": { \"difficulty\": %s }}}", difficulty.getDifficulty());
    }
}
