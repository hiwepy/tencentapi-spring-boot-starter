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
 * Configuration properties for the Tencent Cloud SMS integration.
 * <p>
 * Bound to the {@code tencent.cloud.sms.*} namespace. When {@link #isEnabled()}
 * is {@code true} the {@link TencentSmsAutoConfiguration} creates a
 * {@link com.tencentcloud.spring.boot.sms.TencentSmsTemplate} and the underlying
 * {@code SmsClient}. Credentials fall back to the shared
 * {@link TencentCloudProperties} when not overridden here.</p>
 *
 * <h3>Configuration</h3>
 * <ul>
 *   <li>{@code tencent.cloud.sms.enabled} — opt-in switch (default {@code false})</li>
 *   <li>{@code tencent.cloud.sms.sdkappid} — SMS SdkAppid from the SMS console (e.g. {@code 1400006666})</li>
 *   <li>{@code tencent.cloud.sms.sign} — approved SMS signature (UTF-8, required for mainland-China SMS)</li>
 *   <li>{@code tencent.cloud.sms.sender-id} — international/HK/Macau/TW sender id (empty for mainland China)</li>
 *   <li>{@code tencent.cloud.sms.encode} — SMS code number extension (empty by default)</li>
 *   <li>{@code tencent.cloud.sms.session} — passthrough session context returned verbatim by the server</li>
 *   <li>{@code tencent.cloud.sms.region} — service region, e.g. {@code ap-guangzhou}</li>
 *   <li>{@code tencent.cloud.sms.sign-method} — signature algorithm (default {@code TC3-HMAC-SHA256})</li>
 *   <li>{@code tencent.cloud.sms.unsigned-payload} — exclude payload from signing (default {@code false})</li>
 *   <li>{@code tencent.cloud.sms.language} — response language (default {@code ZH_CN})</li>
 * </ul>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@ConfigurationProperties(TencentSmsProperties.PREFIX)
@Data
public class TencentSmsProperties {

	/** Configuration prefix for Tencent Cloud SMS properties. */
	public static final String PREFIX = "tencent.cloud.sms";

	/** Whether the Tencent SMS integration should be activated (default {@code false}). */
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

	/**
	 * SMS SdkAppid generated when an application is added in the
	 * <a href="https://console.cloud.tencent.com/smsv2">SMS console</a>,
	 * e.g. {@code 1400006666}.
	 */
	private String sdkappid;

	/**
	 * Approved SMS signature (UTF-8). Must match a signature that has passed
	 * review; required for mainland-China SMS.
	 */
	private String sign;

	/**
	 * Sender id for international / HK / Macau / TW SMS. Leave empty for
	 * mainland-China SMS. Disabled by default; contact
	 * <a href="https://cloud.tencent.com/document/product/382/3773">sms helper</a>
	 * to enable.
	 */
	private String senderId;

	/**
	 * SMS code number extension. Disabled by default; contact
	 * <a href="https://cloud.tencent.com/document/product/382/3773">sms helper</a>
	 * to enable.
	 */
	private String encode;

	/**
	 * Caller-provided session context (e.g. user-side id) that the server
	 * returns verbatim. See
	 * <a href="https://cloud.tencent.com/document/product/382/38778">the docs</a>.
	 */
	private String session;

	/** Service region for the SMS client, e.g. {@code ap-guangzhou}. */
  	private String region;

	/** Tencent SDK HTTP profile (endpoint, protocol, proxy, timeouts). */
    private HttpProfile httpProfile = new HttpProfile();

	/** Signature algorithm used by the SMS client (default {@code TC3-HMAC-SHA256}). */
    private String signMethod = ClientProfile.SIGN_TC3_256;

	/**
	 * If {@code true} the request payload is excluded from the signature
	 * computation (default {@code false}).
	 */
    private boolean unsignedPayload;

	/** Response language: {@code ZH_CN} or {@code EN_US} (default {@code ZH_CN}). */
    private Language language = Language.ZH_CN;

}
