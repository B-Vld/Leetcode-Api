package com.leetcode.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.api.constants.Constants;
import com.leetcode.api.model.Difficulty;
import com.leetcode.api.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class RandomQuestionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RandomQuestionService.class);

    @Autowired
    private ResponseFetcherService responseFetcherService;

    public Optional<Question> fetchRandomQuestion(Difficulty difficulty) {
        var response = responseFetcherService.fetchResponse(Constants.QUERY_RANDOM_PROBLEM(difficulty));
        if (response.isPresent()) {
            return toRandomQuestion(response.get(), difficulty);
        }
        return Optional.empty();
    }

    private Optional<Question> toRandomQuestion(String response, Difficulty difficulty) {
        LOGGER.info("Mapping the response : {} to json", response);
        var mapper = new ObjectMapper();
        try {
            var jsonRootNode = mapper.readTree(response);
            var jsonDataNode = jsonRootNode.get("data");
            var jsonRQ = jsonDataNode.get("randomQuestion");
            var jsonTopicTags = jsonRQ.get("topicTags");

            var title = jsonRQ.get("title").asText();
            var questionId = jsonRQ.get("questionId").asText();
            var likes = Integer.valueOf(jsonRQ.get("likes").asText());
            var dislikes = Integer.valueOf(jsonRQ.get("dislikes").asText());
            var submitUrl = Constants.LEETCODE_BASE_URL + jsonRQ.get("submitUrl").asText();
            var link = submitUrl.substring(0, submitUrl.length() - 7);
            var acceptanceRate = jsonRQ.get("acRate").asText();
            var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            var date = dateFormat.format(new Date());

            var topicTags = new ArrayList<String>();

            for (var node : jsonTopicTags) {
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

        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to map the response to json : {0}", e);
        }
        return Optional.empty();
    }


}
