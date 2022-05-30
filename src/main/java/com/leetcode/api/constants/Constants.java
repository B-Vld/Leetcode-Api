package com.leetcode.api.constants;

import com.leetcode.api.model.Difficulty;

import java.util.Set;

public final class Constants {

    public static final String LEETCODE_BASE_URL = "https://leetcode.com";
    public static final String LEETCODE_GRAPHQL_URL = "https://leetcode.com/graphql";
    public static final String LEETCODE_PROBLEMSETS = "https://leetcode.com/problemset/all/";
    public static final String LEETCODE_THUMBNAIL_URL = "https://images.velog.io/images/shiningcastle/post/0522811d-f38d-46fd-a55b-64fd835fc6ad/leetcode.png";

    public static final String JSON = "application/json";
    public static final String QUERY_QUESTION_OF_TODAY = "{\"query\":\"query questionOfToday { activeDailyCodingChallengeQuestion {date link question {acRate submitUrl questionId likes dislikes difficulty title topicTags { name } } } } \",\"variables\":{}}";
    public static final String WEBHOOK_URL = System.getenv().get("WEBHOOK_URL");
    public static final String USER_AGENT = System.getenv().get("USER_AGENT");
    public static final String EMBED_COLOR = "16760576";

    private Constants() {
    }

    public static String QUERY_RANDOM_PROBLEM(Difficulty difficulty) {
        return String.format("{\"query\":\"query randomQuestion($categorySlug: String, $filters: QuestionListFilterInput) { randomQuestion(categorySlug: $categorySlug, filters: $filters) { title questionId difficulty likes dislikes submitUrl topicTags { name } acRate } } \",\"variables\":{ \"categorySlug\":\"\", \"filters\":{ \"difficulty\":\"%s\" } }}", difficulty.getDifficulty());
    }

    public static String QUERY_RANDOM_PROBLEM_TAGS(Difficulty difficulty, Set<String> tags) {
        return String.format("{\"query\":\"query problemsetQuestionList($categorySlug: String, $limit: Int, $skip: Int, $filters: QuestionListFilterInput) { problemsetQuestionList: questionList( categorySlug: $categorySlug limit: $limit skip: $skip filters: $filters ) { total: totalNum questions: data { questionId title submitUrl difficulty acRate topicTags { name } likes dislikes } } } \",\"variables\":{ \"categorySlug\":\"\", \"filters\":{ \"difficulty\":\"%s\", \"tags\":[\"%s\"] } }}", difficulty.getDifficulty(), tags.toString());
    }

}
