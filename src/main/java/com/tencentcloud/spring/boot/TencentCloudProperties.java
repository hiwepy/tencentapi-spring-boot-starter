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

import lombok.Data;

/**
 * Root configuration properties shared by all Tencent Cloud service integrations
 * (Live, SMS, TIM, TRTC, etc.).
 * <p>
 * Bound to the {@code tencent.cloud.*} namespace. The credentials defined here
 * act as a global fallback; each service-specific properties class
 * ({@link TencentLiveProperties}, {@link TencentSmsProperties},
 * {@link TencentTimProperties}, {@link TencentTrtcProperties}) can override the
 * {@code secretId} / {@code secretKey} per service when non-blank values are
 * supplied there.</p>
 *
 * <h3>Configuration</h3>
 * <ul>
 *   <li>{@code tencent.cloud.secret-id} — Tencent Cloud API key (AK)</li>
 *   <li>{@code tencent.cloud.secret-key} — Tencent Cloud API secret (SK)</li>
 *   <li>{@code tencent.cloud.debug} — enable SDK debug logging (default {@code false})</li>
 * </ul>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@ConfigurationProperties(TencentCloudProperties.PREFIX)
@Data
public class TencentCloudProperties {

	/** Configuration prefix shared by all Tencent Cloud service starters. */
	public static final String PREFIX = "tencent.cloud";

	/**
	 * Tencent Cloud API key (AK / secret id) obtained from the
	 * <a href="https://console.cloud.tencent.com/cam/capi">CAM console</a>.
	 */
	private String secretId;

	/**
	 * Tencent Cloud API secret (SK / secret key) obtained from the
	 * <a href="https://console.cloud.tencent.com/cam/capi">CAM console</a>.
	 */
  	private String secretKey;

	/** Whether to enable SDK debug output on the underlying Tencent clients (default {@code false}). */
    private boolean debug;

}
