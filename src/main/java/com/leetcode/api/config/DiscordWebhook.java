package com.leetcode.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.api.constants.Constants;
import com.leetcode.api.service.DailyChallengeService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DiscordWebhook {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordWebhook.class);

    @Autowired
    private OkHttpClient httpClient;

    @Autowired
    private DailyChallengeService dailyChallengeService;

    @Scheduled(cron = "0 0 14 * * *")
    public void sendWebhookRequest() throws IOException {
        var maybeDailyChallenge = dailyChallengeService.fetchDailyChallenge();
        if (maybeDailyChallenge.isPresent()) {
            var dailyChallenge = maybeDailyChallenge.get();
            LOGGER.info("Fetching the daily challenge {}", dailyChallenge);

            var mapper = new ObjectMapper();
            var node = mapper.createObjectNode();
            node.put("content", "@here \n" + dailyChallenge.getLink());
            var json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);

            var body = RequestBody.create(json, MediaType.parse(Constants.JSON));
            LOGGER.info("Creating the body for the webhook request {}", body);

            var request = new Request.Builder()
                    .url(Constants.WEBHOOK_URL)
                    .method("POST", body)
                    .addHeader("referer", Constants.LEETCODE_PROBLEMSETS)
                    .addHeader("Content-Type", Constants.JSON)
                    .addHeader("User-Agent", Constants.USER_AGENT)
                    .build();

            httpClient.newCall(request).execute();
            LOGGER.info("Sending the message");
        }

    }

}
