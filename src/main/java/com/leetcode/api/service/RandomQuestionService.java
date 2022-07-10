package com.leetcode.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.api.constants.Constants;
import com.leetcode.api.model.Difficulty;
import com.leetcode.api.model.Question;
import com.leetcode.api.parser.QuestionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RandomQuestionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RandomQuestionService.class);

    @Autowired
    private ResponseFetcherService responseFetcherService;

    @Autowired
    private QuestionParser questionParser;

    @Autowired
    private ObjectMapper mapper;

    public Optional<Question> fetchRandomQuestionByDifficulty(Difficulty difficulty) {
        var response = responseFetcherService.fetchResponse(Constants.QUERY_RANDOM_PROBLEM(difficulty));
        if (response.isPresent()) {
            return toRandomQuestion(response.get());
        }
        return Optional.empty();
    }

    public Optional<Set<Question>> fetchRandomQuestionByTags(Difficulty difficulty, Set<String> tags) {
        var response = responseFetcherService.fetchResponse(Constants.QUERY_RANDOM_PROBLEM_TAGS(difficulty, tags));
        if (response.isPresent()) {
            return toRandomQuestionsSet(response.get());
        }
        return Optional.empty();
    }

    private Optional<Question> toRandomQuestion(String response) {
        LOGGER.info("Mapping the response : {} to json", response);
        try {
            var jsonRootNode = mapper.readTree(response);
            var jsonDataNode = jsonRootNode.get("data");
            var jsonRQ = jsonDataNode.get("randomQuestion");

            return Optional.of(questionParser.parseNodeToQuestion(jsonRQ));

        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to map the response to json : {0}", e);
        }
        return Optional.empty();
    }

    private Optional<Set<Question>> toRandomQuestionsSet(String response) {
        LOGGER.info("Mapping the response : {} to json", response);
        try {
            var jsonRootNode = mapper.readTree(response);
            var jsonDataNode = jsonRootNode.get("data");
            var jsonProblemsetQuestionList = jsonDataNode.get("problemsetQuestionList");
            var jsonQuestions = jsonProblemsetQuestionList.get("questions");

            var questionSet = new HashSet<Question>();
            for (var questionNode : jsonQuestions) {
                questionSet.add(questionParser.parseNodeToQuestion(questionNode));
            }
            return Optional
                    .of(questionSet);

        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to map the response to json : {0}", e);
        }
        return Optional.empty();
    }


}
