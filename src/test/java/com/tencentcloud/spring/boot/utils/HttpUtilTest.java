package com.tencentcloud.spring.boot.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HttpUtil}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
class HttpUtilTest {

    @Test
    void contentTypeShouldBeCorrect() {
        assertThat(HttpUtil.CONTENT_TYPE).isEqualTo("application/x-www-form-urlencoded");
    }
}
