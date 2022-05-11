package com.leetcode.api.service;

import com.leetcode.api.constants.Constants;
import okhttp3.*;
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

    public Optional<ResponseBody> fetchResponse(String query) {
        var body = RequestBody.create(query, Constants.JSON_MEDIA);
        var request = new Request.Builder()
                .url(Constants.LEETCODE_GRAPHQL_URL)
                .method("POST", body)
                .addHeader("referer", Constants.LEETCODE_PROBLEMSETS)
                .addHeader("Content-Type", Constants.JSON)
                .build();
        try (var response = httpClient.newCall(request).execute()) {
            return Optional.ofNullable(response.body());
        } catch (IOException e) {
            LOGGER.error("The http client failed to fetch a response : ", e);
        }
        return Optional.empty();
    }

}
