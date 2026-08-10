package com.tencentcloud.spring.boot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for all inner classes of {@link TencentTimConstants}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
class TencentTimConstantsFullTest {

    @Test
    void actionConstantsShouldBeCorrect() {
        assertThat(TencentTimConstants.Action.LOGIN).isEqualTo("Login");
        assertThat(TencentTimConstants.Action.LOGOUT).isEqualTo("Logout");
        assertThat(TencentTimConstants.Action.DISCONNECT).isEqualTo("Disconnect");
    }

    @Test
    void stateConstantsShouldBeCorrect() {
        assertThat(TencentTimConstants.State.ONLINE).isEqualTo("Online");
        assertThat(TencentTimConstants.State.PUSHONLINE).isEqualTo("PushOnline");
        assertThat(TencentTimConstants.State.OFFLINE).isEqualTo("Offline");
    }

    @Test
    void reasonConstantsShouldBeCorrect() {
        assertThat(TencentTimConstants.Reason.LINK_CLOSE).isEqualTo("LinkClose");
        assertThat(TencentTimConstants.Reason.TIME_OUT).isEqualTo("TimeOut");
    }

    @Test
    void callBackConstantsShouldNotBeEmpty() {
        assertThat(TencentTimConstants.CallBack.STATE_CHANGE).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.SNS_FRIEND_ADD).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.SNS_FRIEND_DELETE).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.SNS_BLACKLIST_ADD).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.SNS_BLACKLIST_DELETE).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.C2C_BEFORE_SEND_MSG).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.C2C_AFTER_SEND_MSG).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_BEFORE_CREATE).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_AFTER_CREATE).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_BEFORE_APPLY_JOIN).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_BEFORE_INVITE_JOIN).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_AFTER_MEMBER_JOIN).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_AFTER_MEMBER_EXIT).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_BEFORE_SEND_MSG).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_AFTER_SEND_MSG).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_AFTER_GROUP_FULL).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_AFTER_GROUP_DESTROYED).isNotEmpty();
        assertThat(TencentTimConstants.CallBack.GROUP_AFTER_GROUP_INFO_CHANGED).isNotEmpty();
    }
}
