package com.leetcode.api.model.discord;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@ToString
public class Embed {

    private final String title;

    private final String url;

    private final String description;

    private final String color;

    private final List<Field> fields;

    private final Thumbnail thumbnail;

}
