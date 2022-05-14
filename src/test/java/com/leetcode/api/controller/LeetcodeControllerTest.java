package com.leetcode.api.controller;

import com.leetcode.api.constants.Constants;
import com.leetcode.api.model.Difficulty;
import com.leetcode.api.model.Question;
import com.leetcode.api.service.DailyChallengeService;
import com.leetcode.api.service.RandomQuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LeetcodeController.class)
public class LeetcodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DailyChallengeService dailyChallengeService;

    @MockBean
    private RandomQuestionService randomQuestionService;

    @Test
    void daily_challenge_test_should_have_success_status() throws Exception {
        when(dailyChallengeService.fetchDailyChallenge()).thenReturn(Optional.of(mockQuestion()));

        MvcResult mvcResult = mockMvc.perform(get("/api/daily-challenge"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("dummyId")))
                .andReturn();

        assertEquals(Constants.JSON, mvcResult.getResponse().getContentType());
    }

    @Test
    void random_question_test_should_have_success_status() throws Exception {
        when(randomQuestionService.fetchRandomQuestion(Difficulty.EASY)).thenReturn(Optional.of(mockQuestion()));

        MvcResult mvcResult = mockMvc.perform(get(String.format("/api/random-question/%s", Difficulty.EASY.getDifficulty())))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("dummyId")))
                .andReturn();

        assertEquals(Constants.JSON, mvcResult.getResponse().getContentType());
    }

    private Question mockQuestion() {
        return Question.builder()
                .date("10-10-1010")
                .acceptanceRate("10.10")
                .difficulty(Difficulty.EASY)
                .dislikes(10)
                .questionId("dummyId")
                .likes(10)
                .link("dummyLink")
                .title("dummyTitle")
                .topicTags(List.of("dummyTag1", "dummyTag2"))
                .build();
    }

}