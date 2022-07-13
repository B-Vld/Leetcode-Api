package com.leetcode.api.config;

import com.leetcode.api.model.Difficulty;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class DifficultyConverter implements Converter<String, Difficulty> {

    @Override
    public Difficulty convert(@NotNull String value) {
        return Difficulty.of(value);
    }

}
