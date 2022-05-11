package com.leetcode.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.api.constants.Constants;
import com.leetcode.api.model.Difficulty;
import com.leetcode.api.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

@Service
public class DailyChallengeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyChallengeService.class);

    @Autowired
    private ResponseFetcherService responseFetcherService;

    public Optional<Question> fetchDailyChallenge() {
        var maybeResponseBody = responseFetcherService.fetchResponse(Constants.QUERY_QUESTION_OF_TODAY);
            if (maybeResponseBody.isPresent()) {
                try {
                    var jsonBody = maybeResponseBody.get().string();
                    return toQuestion(jsonBody);
                } catch (IOException e) {
                    LOGGER.error("Failed to fetch response body ", e);
                }
            }
        return Optional.empty();
    }

    private Optional<Question> toQuestion(String response) throws JsonProcessingException {
        var mapper = new ObjectMapper();

        var jsonRootNode = mapper.readTree(response);
        var jsonDataNode = jsonRootNode.get("data");
        var jsonDCQNode = jsonDataNode.get("activeDailyCodingChallengeQuestion");
        var jsonQuestionNode = jsonDCQNode.get("question");
        var jsonTopicTags = jsonQuestionNode.get("topicTags");

        var questionId = jsonQuestionNode.get("questionId").asText();
        var date = jsonDCQNode.get("date").asText();
        var link = Constants.LEETCODE_BASE_URL + jsonDCQNode.get("link").asText();
        var acceptanceRate = jsonQuestionNode.get("acRate").asText();
        var difficulty = Difficulty.valueOf(jsonQuestionNode.get("difficulty").asText().toUpperCase(Locale.ROOT));
        var title = jsonQuestionNode.get("title").asText();
        var likes = Integer.valueOf(jsonQuestionNode.get("likes").asText());
        var dislikes = Integer.valueOf(jsonQuestionNode.get("dislikes").asText());
        var topicTags = new ArrayList<String>();

        for (JsonNode node : jsonTopicTags) {
            topicTags.add(node.get("name").asText());
        }

        var question = Question.builder()
                .questionId(questionId)
                .title(title)
                .link(link)
                .difficulty(difficulty)
                .acceptanceRate(acceptanceRate)
                .topicTags(topicTags)
                .likes(likes)
                .dislikes(dislikes)
                .date(date)
                .build();

        return Optional.of(question);
    }

}
