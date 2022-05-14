package com.leetcode.api.service;

import com.leetcode.api.constants.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ResponseFetcherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseFetcherService.class);

    @Autowired
    private OkHttpClient httpClient;

    public Optional<String> fetchResponse(String query) {
        LOGGER.info("Sending a POST request to : {} with the following query : {} ", Constants.LEETCODE_GRAPHQL_URL, query);
        var body = RequestBody.create(query, MediaType.parse(Constants.JSON));
        var request = new Request.Builder()
                .url(Constants.LEETCODE_GRAPHQL_URL)
                .method("POST", body)
                .addHeader("referer", Constants.LEETCODE_PROBLEMSETS)
                .addHeader("Content-Type", Constants.JSON)
                .build();
        try (var response = httpClient.newCall(request).execute()) {
            var responseBody = Optional.ofNullable(response.body());
            if (responseBody.isPresent()) {
                return Optional.of(responseBody.get().string());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to fetch response : {0}", e);
        }
        return Optional.empty();
    }

}
