package com.leetcode.api.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.leetcode.api.constants.Constants;
import com.leetcode.api.model.Difficulty;
import com.leetcode.api.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class QuestionParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(QuestionParser.class);

    public Question parseNodeToQuestion(JsonNode jsonNode) {
        LOGGER.info("Parsing the json node to a Question object");

        var jsonTopicTags = jsonNode.get("topicTags");

        var title = jsonNode.get("title").asText();
        var questionId = jsonNode.get("questionId").asText();
        var likes = Integer.valueOf(jsonNode.get("likes").asText());
        var dislikes = Integer.valueOf(jsonNode.get("dislikes").asText());
        var submitUrl = Constants.LEETCODE_BASE_URL + jsonNode.get("submitUrl").asText();
        var link = submitUrl.substring(0, submitUrl.length() - 7);
        var acceptanceRate = jsonNode.get("acRate").asText();
        var difficulty = jsonNode.get("difficulty").asText();

        var topicTags = new ArrayList<String>();

        for (var node : jsonTopicTags) {
            topicTags.add(node.get("name").asText());
        }

        return Question.builder()
                .questionId(questionId)
                .title(title)
                .link(link)
                .difficulty(Difficulty.of(difficulty))
                .acceptanceRate(acceptanceRate)
                .topicTags(topicTags)
                .likes(likes)
                .dislikes(dislikes)
                .build();

    }

}
