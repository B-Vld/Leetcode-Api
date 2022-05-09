package com.leetcode.api.constants;

public final class Constants {

    private Constants() {}

    public static final String LEETCODE_BASE_URL = "https://leetcode.com";
    public static final String LEETCODE_GRAPHQL_URL = "https://leetcode.com/graphql";
    public static final String LEETCODE_PROBLEMSETS = "https://leetcode.com/problemset/all/";

    public static final String QUERY_QUESTION_OF_TODAY = "{\"query\":\"query questionOfToday { activeDailyCodingChallengeQuestion {date link question {acRate difficulty title topicTags { name } } } } \",\"variables\":{}}";
}
