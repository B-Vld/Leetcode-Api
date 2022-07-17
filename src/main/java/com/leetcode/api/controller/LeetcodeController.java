package com.leetcode.api.controller;

import com.leetcode.api.model.Difficulty;
import com.leetcode.api.model.Question;
import com.leetcode.api.service.DailyChallengeService;
import com.leetcode.api.service.RandomQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LeetcodeController {

    @Autowired
    private DailyChallengeService dailyChallengeService;

    @Autowired
    private RandomQuestionService randomQuestionService;

    @GetMapping(path = "/daily-challenge", produces = "application/json")
    public ResponseEntity<Question> fetchDailyChallenge() {
        return dailyChallengeService
                .fetchDailyChallenge()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/random-question/{difficulty}", produces = "application/json")
    public ResponseEntity<Question> fetchRandomQuestionBasedOfDifficulty(@PathVariable("difficulty") Difficulty difficulty) {
        return randomQuestionService
                .fetchRandomQuestionByDifficulty(difficulty)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/random-question/{difficulty}", produces = "application/json")
    public ResponseEntity<Set<Question>> fetchRandomQuestionBasedOnTopicTags(@PathVariable("difficulty") Difficulty difficulty, @RequestBody Set<String> tags) {
        return randomQuestionService
                .fetchRandomQuestionByTags(difficulty, tags)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
