/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tencentcloud.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.profile.Language;

import lombok.Data;

/**
 * Configuration properties for the Tencent Real-Time Communication (TRTC)
 * integration.
 * <p>
 * Bound to the {@code tencent.cloud.trtc.*} namespace. When {@link #isEnabled()}
 * is {@code true} the {@link TencentTrtcAutoConfiguration} creates a
 * {@link com.tencentcloud.spring.boot.trtc.TencentTrtcTemplate} together with
 * the underlying {@code TrtcClient}. Credentials fall back to the shared
 * {@link TencentCloudProperties} when not overridden here.</p>
 *
 * <h3>Configuration</h3>
 * <ul>
 *   <li>{@code tencent.cloud.trtc.enabled} — opt-in switch (default {@code false})</li>
 *   <li>{@code tencent.cloud.trtc.sdkappid} — TRTC application SdkAppid</li>
 *   <li>{@code tencent.cloud.trtc.region} — service region, e.g. {@code ap-guangzhou}</li>
 *   <li>{@code tencent.cloud.trtc.sign-method} — signature algorithm (default {@code TC3-HMAC-SHA256})</li>
 *   <li>{@code tencent.cloud.trtc.unsigned-payload} — exclude payload from signing (default {@code false})</li>
 *   <li>{@code tencent.cloud.trtc.language} — response language (default {@code ZH_CN})</li>
 *   <li>{@code tencent.cloud.trtc.push-domain} / {@code play-domain} / {@code app-name} — streaming domain parameters</li>
 *   <li>{@code tencent.cloud.trtc.retry-times} — operation retry count (default {@code 2})</li>
 * </ul>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@ConfigurationProperties(TencentTrtcProperties.PREFIX)
@Data
public class TencentTrtcProperties {

	/** Configuration prefix for Tencent TRTC properties. */
	public static final String PREFIX = "tencent.cloud.trtc";

	/** Whether the Tencent TRTC integration should be activated (default {@code false}). */
	private boolean enabled = false;

	/** TRTC application SdkAppid obtained from the TRTC console. */
    private Long sdkappid;

	/**
	 * Per-service override of the Tencent Cloud API key (AK).
	 * When blank the value from {@link TencentCloudProperties#getSecretId()} is used.
	 */
	private String secretId;

	/**
	 * Per-service override of the Tencent Cloud API secret (SK).
	 * When blank the value from {@link TencentCloudProperties#getSecretKey()} is used.
	 */
  	private String secretKey;

	/** Service region for the TRTC client, e.g. {@code ap-guangzhou}. */
	private String region;

	/** Tencent SDK HTTP profile (endpoint, protocol, proxy, timeouts). */
    private HttpProfile httpProfile = new HttpProfile();

	/** Signature algorithm used by the TRTC client (default {@code TC3-HMAC-SHA256}). */
    private String signMethod = ClientProfile.SIGN_TC3_256;

	/**
	 * If {@code true} the request payload is excluded from the signature
	 * computation (default {@code false}).
	 */
    private boolean unsignedPayload;

	/** Response language: {@code ZH_CN} or {@code EN_US} (default {@code ZH_CN}). */
    private Language language = Language.ZH_CN;

	/** Push (ingest) domain used to build TRTC stream URLs. */
  	private String pushDomain;

	/** Playback domain used to build TRTC play URLs. */
    private String playDomain;

	/** App name segment used in the TRTC stream URL path. */
    private String appName;

	/** Number of times to retry failed TRTC operations (default {@code 2}). */
    private Integer retryTimes = 2;

}
