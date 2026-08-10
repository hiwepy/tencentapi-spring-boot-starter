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
 * Configuration properties for the Tencent Cloud Live (LVB) integration.
 * <p>
 * Bound to the {@code tencent.cloud.live.*} namespace. When {@link #isEnabled()}
 * is {@code true} the {@link TencentLiveAutoConfiguration} creates a
 * {@link com.tencentcloud.spring.boot.live.TencentLiveTemplate} together with
 * the underlying {@code LiveClient}. Credentials fall back to the shared
 * {@link TencentCloudProperties} when not overridden here.</p>
 *
 * <h3>Configuration</h3>
 * <ul>
 *   <li>{@code tencent.cloud.live.enabled} — opt-in switch (default {@code false})</li>
 *   <li>{@code tencent.cloud.live.secret-id} / {@code secret-key} — per-service credential override</li>
 *   <li>{@code tencent.cloud.live.region} — service region, e.g. {@code ap-guangzhou}</li>
 *   <li>{@code tencent.cloud.live.sign-method} — signature algorithm (default {@code TC3-HMAC-SHA256})</li>
 *   <li>{@code tencent.cloud.live.unsigned-payload} — exclude the request payload from signing (default {@code false})</li>
 *   <li>{@code tencent.cloud.live.language} — response language, {@code ZH_CN} or {@code EN_US} (default {@code ZH_CN})</li>
 *   <li>{@code tencent.cloud.live.push-domain} / {@code play-domain} / {@code app-name} / {@code stream-url-key} — live streaming domain parameters</li>
 *   <li>{@code tencent.cloud.live.retry-times} — operation retry count (default {@code 2})</li>
 * </ul>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@ConfigurationProperties(TencentLiveProperties.PREFIX)
@Data
public class TencentLiveProperties {

	/** Configuration prefix for Tencent Cloud Live properties. */
	public static final String PREFIX = "tencent.cloud.live";

	/** Whether the Tencent Live integration should be activated (default {@code false}). */
	private boolean enabled = false;

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

	/** Service region for the Live client, e.g. {@code ap-guangzhou}. */
  	private String region;

	/** Tencent SDK HTTP profile (endpoint, protocol, proxy, timeouts). */
    private HttpProfile httpProfile = new HttpProfile();

	/** Signature algorithm used by the Live client (default {@code TC3-HMAC-SHA256}). */
    private String signMethod = ClientProfile.SIGN_TC3_256;

	/**
	 * If {@code true} the request payload is excluded from the signature
	 * computation (default {@code false}).
	 */
    private boolean unsignedPayload;

	/** Response language: {@code ZH_CN} or {@code EN_US} (default {@code ZH_CN}). */
    private Language language = Language.ZH_CN;

	/** Push (ingest) domain used to build live push URLs. */
  	private String pushDomain;

	/** Playback domain used to build live play URLs. */
    private String playDomain;

	/** App name segment used in the live push/play URL path. */
    private String appName;

	/** Authentication key used to sign live stream URLs. */
    private String streamUrlKey;

	/** Number of times to retry failed Live operations (default {@code 2}). */
    private Integer retryTimes = 2;

}
