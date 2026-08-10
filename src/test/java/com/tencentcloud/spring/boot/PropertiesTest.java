package com.tencentcloud.spring.boot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for all Properties classes.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
class PropertiesTest {

    @Test
    void cloudPropertiesShouldWork() {
        TencentCloudProperties props = new TencentCloudProperties();
        props.setSecretId("testId");
        props.setSecretKey("testKey");
        props.setDebug(true);
        assertThat(props.getSecretId()).isEqualTo("testId");
        assertThat(props.getSecretKey()).isEqualTo("testKey");
        assertThat(props.isDebug()).isTrue();
        assertThat(props.toString()).isNotEmpty();
        assertThat(props.equals(props)).isTrue();
        assertThat(props.hashCode()).isNotZero();
    }

    @Test
    void livePropertiesShouldWork() {
        TencentLiveProperties props = new TencentLiveProperties();
        props.setSecretId("liveId");
        props.setSecretKey("liveKey");
        props.setRegion("ap-guangzhou");
        assertThat(props.getSecretId()).isEqualTo("liveId");
        assertThat(props.getSecretKey()).isEqualTo("liveKey");
        assertThat(props.getRegion()).isEqualTo("ap-guangzhou");
    }

    @Test
    void smsPropertiesShouldWork() {
        TencentSmsProperties props = new TencentSmsProperties();
        props.setSecretId("smsId");
        props.setSecretKey("smsKey");
        props.setRegion("ap-guangzhou");
        props.setSdkappid("1400000000");
        props.setSign("testSign");
        props.setSenderId("sender1");
        props.setEncode("encode1");
        props.setSession("session1");
        props.setEnabled(true);
        assertThat(props.getSecretId()).isEqualTo("smsId");
        assertThat(props.getSecretKey()).isEqualTo("smsKey");
        assertThat(props.getRegion()).isEqualTo("ap-guangzhou");
        assertThat(props.getSdkappid()).isEqualTo("1400000000");
        assertThat(props.getSign()).isEqualTo("testSign");
        assertThat(props.getSenderId()).isEqualTo("sender1");
        assertThat(props.getEncode()).isEqualTo("encode1");
        assertThat(props.getSession()).isEqualTo("session1");
        assertThat(props.isEnabled()).isTrue();
    }

    @Test
    void timPropertiesShouldWork() {
        TencentTimProperties props = new TencentTimProperties();
        props.setSdkappid(1400288577L);
        props.setPrivateKey("testKey");
        props.setIdentifier("admin");
        props.setDomain("https://console.tim.qq.com");
        props.setEnabled(true);
        assertThat(props.getSdkappid()).isEqualTo(1400288577L);
        assertThat(props.getPrivateKey()).isEqualTo("testKey");
        assertThat(props.getIdentifier()).isEqualTo("admin");
        assertThat(props.getDomain()).isEqualTo("https://console.tim.qq.com");
        assertThat(props.isEnabled()).isTrue();
        assertThat(props.toString()).isNotEmpty();
    }

    @Test
    void trtcPropertiesShouldWork() {
        TencentTrtcProperties props = new TencentTrtcProperties();
        props.setSdkappid(1400000000L);
        props.setSecretId("trtcId");
        props.setSecretKey("trtcKey");
        assertThat(props.getSdkappid()).isEqualTo(1400000000L);
        assertThat(props.getSecretId()).isEqualTo("trtcId");
        assertThat(props.getSecretKey()).isEqualTo("trtcKey");
    }
}
