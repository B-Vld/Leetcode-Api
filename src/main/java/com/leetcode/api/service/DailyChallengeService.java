package com.leetcode.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.api.constants.Constants;
import com.leetcode.api.model.Question;
import com.leetcode.api.parser.QuestionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DailyChallengeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyChallengeService.class);

    @Autowired
    private ResponseFetcherService responseFetcherService;

    @Autowired
    private QuestionParser parser;

    public Optional<Question> fetchDailyChallenge() {
        var response = responseFetcherService.fetchResponse(Constants.QUERY_QUESTION_OF_TODAY);
        if (response.isPresent()) {
            return toDailyQuestion(response.get());
        }
        return Optional.empty();
    }

    private Optional<Question> toDailyQuestion(String response) {
        LOGGER.info("Mapping the response : {} to json", response);
        var mapper = new ObjectMapper();
        try {
            var jsonRootNode = mapper.readTree(response);
            var jsonDataNode = jsonRootNode.get("data");
            var jsonDCQNode = jsonDataNode.get("activeDailyCodingChallengeQuestion");
            var jsonQuestionNode = jsonDCQNode.get("question");

            return Optional.of(parser.parseNodeToQuestion(jsonQuestionNode));

        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to map the response to json : {0}", e);
        }
        return Optional.empty();
    }

}
