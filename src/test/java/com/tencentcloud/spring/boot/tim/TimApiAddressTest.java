package com.tencentcloud.spring.boot.tim;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TimApiAddress}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
class TimApiAddressTest {

    @Test
    void allEnumValuesShouldHaveOptAndUrl() {
        for (TimApiAddress address : TimApiAddress.values()) {
            assertThat(address.getOpt()).isNotEmpty();
            assertThat(address.getUrl()).isNotEmpty();
            assertThat(address.getUrl()).startsWith("https://");
        }
    }

    @Test
    void enumShouldHaveExpectedValues() {
        assertThat(TimApiAddress.ACCOUNT_IMPORT.getOpt()).isEqualTo("导入单个帐号");
        assertThat(TimApiAddress.ACCOUNT_IMPORT.getUrl()).contains("account_import");
        assertThat(TimApiAddress.SEND_MSG.getUrl()).contains("sendmsg");
        assertThat(TimApiAddress.CREATE_GROUP.getUrl()).contains("create_group");
    }

    @Test
    void valueOfShouldWork() {
        assertThat(TimApiAddress.valueOf("ACCOUNT_IMPORT")).isEqualTo(TimApiAddress.ACCOUNT_IMPORT);
        assertThat(TimApiAddress.valueOf("SEND_MSG")).isEqualTo(TimApiAddress.SEND_MSG);
    }

    @Test
    void valuesShouldContainAllEntries() {
        TimApiAddress[] values = TimApiAddress.values();
        assertThat(values.length).isGreaterThan(30);
    }
}
