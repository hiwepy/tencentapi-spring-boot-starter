package com.tencentcloud.spring.boot.utils;

import com.tencentcloud.spring.boot.tim.TimApiAddress;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CommonHelper}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
class CommonHelperTest {

    @Test
    void oneWeekSecondShouldBeCorrect() {
        assertThat(CommonHelper.ONE_WEEK_SECOND).isEqualTo(7 * 24 * 60 * 60);
    }

    @Test
    void getMixStreamSessionIdShouldAppendTimestamp() {
        String result = CommonHelper.getMixStreamSessionId("testStream");
        assertThat(result).startsWith("testStream_");
        assertThat(result).contains("_");
    }

    @Test
    void getRtmpUrlShouldBuildCorrectUrl() {
        StringBuilder url = CommonHelper.getRtmpUrl("push.example.com", "live", "stream1", "txSecret=abc&txTime=123");
        assertThat(url.toString()).isEqualTo("rtmp://push.example.com/live/stream1?txSecret=abc&txTime=123");
    }

    @Test
    void getWebrtcUrlShouldBuildCorrectUrl() {
        StringBuilder url = CommonHelper.getWebrtcUrl("push.example.com", "live", "stream1", "txSecret=abc&txTime=123");
        assertThat(url.toString()).isEqualTo("webrtc://push.example.com/live/stream1?txSecret=abc&txTime=123");
    }

    @Test
    void getFlvUrlShouldBuildCorrectUrl() {
        StringBuilder url = CommonHelper.getFlvUrl("play.example.com", "live", "stream1", "txSecret=abc");
        assertThat(url.toString()).isEqualTo("http://play.example.com/live/stream1.flv?txSecret=abc");
    }

    @Test
    void getHlsUrlShouldBuildCorrectUrl() {
        StringBuilder url = CommonHelper.getHlsUrl("play.example.com", "live", "stream1", "txSecret=abc");
        assertThat(url.toString()).isEqualTo("http://play.example.com/live/stream1.m3u8?txSecret=abc");
    }

    @Test
    void getSafeUrlShouldReturnNonEmptyString() {
        String result = CommonHelper.getSafeUrl("testKey", "testStream", 1469762325L);
        assertThat(result).isNotEmpty();
        assertThat(result).contains("txSecret=");
        assertThat(result).contains("txTime=");
    }

    @Test
    void getSafeUrlShouldReturnConsistentResults() {
        String result1 = CommonHelper.getSafeUrl("key", "stream", 1000L);
        String result2 = CommonHelper.getSafeUrl("key", "stream", 1000L);
        assertThat(result1).isEqualTo(result2);
    }

    @Test
    void getRequestUrlShouldReturnBaseUrlWhenDataIsNull() {
        TimApiAddress address = TimApiAddress.ACCOUNT_IMPORT;
        String url = CommonHelper.getRequestUrl(address, null);
        assertThat(url).isEqualTo(address.getUrl());
    }

    @Test
    void getRequestUrlShouldReturnBaseUrlWhenDataIsEmpty() {
        TimApiAddress address = TimApiAddress.ACCOUNT_IMPORT;
        String url = CommonHelper.getRequestUrl(address, new HashMap<>());
        assertThat(url).isEqualTo(address.getUrl());
    }

    @Test
    void getRequestUrlShouldAppendQueryParams() {
        TimApiAddress address = TimApiAddress.ACCOUNT_IMPORT;
        Map<String, String> data = Map.of("key1", "value1", "key2", "value2");
        String url = CommonHelper.getRequestUrl(address, data);
        assertThat(url).contains("key1=value1");
        assertThat(url).contains("key2=value2");
    }

    @Test
    void delimiterShouldBeAmpersand() {
        assertThat(CommonHelper.DELIMITER).isEqualTo("&");
    }

    @Test
    void separatorShouldBeEquals() {
        assertThat(CommonHelper.SEPARATOR).isEqualTo("=");
    }
}
