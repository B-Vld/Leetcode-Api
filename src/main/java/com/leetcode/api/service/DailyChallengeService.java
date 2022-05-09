package com.leetcode.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.api.constants.Constants;
import com.leetcode.api.model.DailyChallengeRepresentation;
import com.leetcode.api.model.Difficulty;
import lombok.AllArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class DailyChallengeService {

    public Optional<DailyChallengeRepresentation> fetchDailyChallenge() {
        var httpClient = new OkHttpClient()
                .newBuilder()
                .build();
        var body = RequestBody.create(Constants.QUERY_QUESTION_OF_TODAY, MediaType.parse("application/json"));
        var request = new Request.Builder()
                .url(Constants.LEETCODE_GRAPHQL_URL)
                .method("POST", body)
                .addHeader("referer", Constants.LEETCODE_PROBLEMSETS)
                .addHeader("Content-Type", "application/json")
                .build();
        try (var response = httpClient.newCall(request).execute()) {
            var maybeResponseBody = Optional.ofNullable(response.body());
            if (maybeResponseBody.isPresent()) {
                var mapper = new ObjectMapper();
                var jsonBody = maybeResponseBody.get().string();
                return toDailyChallengeRepresentation(jsonBody, mapper);
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    public Optional<DailyChallengeRepresentation> toDailyChallengeRepresentation(String response, ObjectMapper mapper) throws JsonProcessingException {
        var jsonRootNode = mapper.readTree(response);
        var jsonDataNode = jsonRootNode.get("data");
        var jsonDCQNode = jsonDataNode.get("activeDailyCodingChallengeQuestion");
        var jsonQuestionNode = jsonDCQNode.get("question");
        var jsonTopicTags = jsonQuestionNode.get("topicTags");

        var date = jsonDCQNode.get("date").asText();
        var link = Constants.LEETCODE_BASE_URL + jsonDCQNode.get("link").asText();
        var acceptanceRate = jsonQuestionNode.get("acRate").asText();
        var difficulty = Difficulty.valueOf(jsonQuestionNode.get("difficulty").asText().toUpperCase(Locale.ROOT));
        var title = jsonQuestionNode.get("title").asText();
        var topicTags = new ArrayList<String>();

        for(JsonNode node : jsonTopicTags) {
            topicTags.add(node.get("name").asText());
        }

        return Optional
                .of(new DailyChallengeRepresentation(title, link, difficulty, acceptanceRate, topicTags, date));
    }

}
