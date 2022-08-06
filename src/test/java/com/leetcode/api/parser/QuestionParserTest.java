package com.leetcode.api.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.api.model.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionParserTest {

    @Mock
    private ObjectMapper mapper;

    @Mock
    private QuestionParser sut;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        sut = new QuestionParser();
    }

    @Test
    void test_parser() throws JsonProcessingException {
        var questionRequest = "{\"questionId\":\"29\",\"acRate\":17.410095866602436,\"difficulty\":\"Medium\",\"title\":\"DivideTwoIntegers\",\"submitUrl\":\"/problems/divide-two-integers/submit/\",\"topicTags\":[{\"name\":\"Math\"},{\"name\":\"BitManipulation\"}],\"likes\":3059,\"dislikes\":10568}";
        var jsonNode = mapper.readTree(questionRequest);

        var questionResponse = sut.parseNodeToQuestion(jsonNode);

        assertThat(questionResponse).isNotNull();
        assertThat(questionResponse.getQuestionId()).isEqualTo("29");
        assertThat(questionResponse.getDifficulty()).isEqualTo(Difficulty.of("Medium"));
    }

}
