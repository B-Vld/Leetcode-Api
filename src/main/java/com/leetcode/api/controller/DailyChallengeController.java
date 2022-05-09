package com.leetcode.api.controller;

import com.leetcode.api.model.DailyChallengeRepresentation;
import com.leetcode.api.service.DailyChallengeService;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DailyChallengeController {

    @Autowired
    private DailyChallengeService dailyChallengeService;

    @GetMapping(path = "/daily-challenge", produces = "application/json")
    public ResponseEntity<DailyChallengeRepresentation> fetchDailyChallenge() {
        return dailyChallengeService
                .fetchDailyChallenge()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
