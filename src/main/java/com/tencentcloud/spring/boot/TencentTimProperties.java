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

import com.tencentcloud.spring.boot.tim.TencentTimOption;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuration properties for the Tencent Cloud IM (TIM) integration.
 * <p>
 * Bound to the {@code tencent.cloud.tim.*} namespace and extends
 * {@link TencentTimOption} which carries the SDKAppid, key and account fields
 * required by the {@code TLSSigAPIv2} user signature generator. When
 * {@link #isEnabled()} is {@code true} the {@link TencentTimAutoConfiguration}
 * creates a {@link com.tencentcloud.spring.boot.tim.TencentTimTemplate}.</p>
 *
 * <h3>Configuration</h3>
 * <ul>
 *   <li>{@code tencent.cloud.tim.enabled} — opt-in switch (default {@code false})</li>
 *   <li>{@code tencent.cloud.tim.domain} — TIM REST API base domain</li>
 * </ul>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(TencentTimProperties.PREFIX)
@Data
public class TencentTimProperties extends TencentTimOption {

	/** Configuration prefix for Tencent Cloud IM (TIM) properties. */
	public static final String PREFIX = "tencent.cloud.tim";

	/** Whether the Tencent IM integration should be activated (default {@code false}). */
	private boolean enabled = false;

	/** Base domain (host) of the TIM REST API endpoints. */
	private String domain;

}
