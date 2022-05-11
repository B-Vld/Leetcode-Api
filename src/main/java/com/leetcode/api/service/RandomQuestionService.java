package com.leetcode.api.service;

import com.leetcode.api.constants.Constants;
import com.leetcode.api.model.Difficulty;
import com.leetcode.api.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RandomQuestionService {

    @Autowired
    private ResponseFetcherService responseFetcherService;

    public Optional<Question> fetchRandomQuestion(Difficulty difficulty) {
        var response = responseFetcherService.fetchResponse(Constants.QUERY_RANDOM_PROBLEM(difficulty));


        return Optional.empty();
    }



}
