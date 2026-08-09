package com.tencentcloud.spring.boot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TencentTimConstants}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
class TencentTimConstantsTest {

    @Test
    void actionLoginShouldBeCorrect() {
        assertThat(TencentTimConstants.Action.LOGIN).isEqualTo("Login");
    }

    @Test
    void actionLogoutShouldBeCorrect() {
        assertThat(TencentTimConstants.Action.LOGOUT).isEqualTo("Logout");
    }

    @Test
    void actionDisconnectShouldBeCorrect() {
        assertThat(TencentTimConstants.Action.DISCONNECT).isEqualTo("Disconnect");
    }

    @Test
    void stateOnlineShouldBeCorrect() {
        assertThat(TencentTimConstants.State.ONLINE).isEqualTo("Online");
    }

    @Test
    void statePushOnlineShouldBeCorrect() {
        assertThat(TencentTimConstants.State.PUSHONLINE).isEqualTo("PushOnline");
    }

    @Test
    void stateOfflineShouldBeCorrect() {
        assertThat(TencentTimConstants.State.OFFLINE).isEqualTo("Offline");
    }

    @Test
    void reasonLinkCloseShouldBeCorrect() {
        assertThat(TencentTimConstants.Reason.LINK_CLOSE).isEqualTo("LinkClose");
    }

    @Test
    void reasonTimeOutShouldBeCorrect() {
        assertThat(TencentTimConstants.Reason.TIME_OUT).isEqualTo("TimeOut");
    }
}
