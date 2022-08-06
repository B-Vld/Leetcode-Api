package com.leetcode.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApiApplicationTests {

    @Test
    void contextLoads(ApplicationContext ctx) {
        assertThat(ctx).isNotNull();
    }

}
