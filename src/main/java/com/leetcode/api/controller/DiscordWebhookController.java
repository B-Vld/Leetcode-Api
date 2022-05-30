package com.leetcode.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.api.constants.Constants;
import com.leetcode.api.model.discord.Embed;
import com.leetcode.api.model.discord.Field;
import com.leetcode.api.model.discord.Thumbnail;
import com.leetcode.api.service.DailyChallengeService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
public class DiscordWebhookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordWebhookController.class);

    @Autowired
    private OkHttpClient httpClient;

    @Autowired
    private DailyChallengeService dailyChallengeService;

    @Scheduled(cron = "0 15 19 * * *", zone = "GMT+3")
    public void sendWebhookRequest() {
        var maybeDailyChallenge = dailyChallengeService.fetchDailyChallenge();
        var mapper = new ObjectMapper();
        if (maybeDailyChallenge.isPresent()) {

            var dailyChallenge = maybeDailyChallenge.get();
            LOGGER.info("Fetching the daily challenge {}", dailyChallenge);

            var fields = List.of(
                    new Field("Difficulty", dailyChallenge.getDifficulty().name(), false),
                    new Field("Topic Tags", dailyChallenge.getTopicTags().toString(), false),
                    new Field("Likes", String.valueOf(dailyChallenge.getLikes()), true),
                    new Field("Dislikes", String.valueOf(dailyChallenge.getDislikes()), true)
            );

            var embed = Embed.builder()
                    .title(dailyChallenge.getTitle())
                    .url(dailyChallenge.getSubmitUrl())
                    .color(Constants.EMBED_COLOR)
                    .thumbnail(new Thumbnail(Constants.LEETCODE_THUMBNAIL_URL))
                    .fields(fields)
                    .build();

            var embeds = new HashMap<String, List<Embed>>();
            embeds.put("embeds", List.of(embed));

            try {
                var json = mapper.writeValueAsString(embeds);

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

            } catch (IOException e) {
                LOGGER.error("Could not execute the request {0}", e);
            }
        }
    }

}
