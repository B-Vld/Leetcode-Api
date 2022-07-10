package com.leetcode.api.controller.thymeleaf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.api.model.Difficulty;
import com.leetcode.api.service.DailyChallengeService;
import com.leetcode.api.service.RandomQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafController.class);

    @Autowired
    private DailyChallengeService dailyChallengeService;

    @Autowired
    private RandomQuestionService randomQuestionService;

    @Autowired
    private ObjectMapper mapper;


    @GetMapping
    String index(Model model) {
        var dailyChallenge = dailyChallengeService.fetchDailyChallenge();
        var randomQuestion = randomQuestionService.fetchRandomQuestionByDifficulty(Difficulty.MEDIUM);
        dailyChallenge.ifPresent(question -> {
            try {
                model.addAttribute("dailyChallenge", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(question));
            } catch (JsonProcessingException e) {
                LOGGER.error(e.getMessage());
            }
        });
        randomQuestion.ifPresent(question -> {
            try {
                model.addAttribute("randomQuestion", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(question));
            } catch (JsonProcessingException e) {
                LOGGER.error(e.getMessage());
            }
        });
        return "index";
    }

}
