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

@ConfigurationProperties(TencentSmsProperties.PREFIX)
public class TencentSmsProperties {

	public static final String PREFIX = "tencent.sms";

	/**
	 * 短信SdkAppid在 [短信控制台](https://console.cloud.tencent.com/smsv2)
	 * 添加应用后生成的实际SdkAppid，示例如1400006666。
	 */
	private String sdkappid;

	private String region;

	/**
	 * 短信签名内容，使用 UTF-8 编码，必须填写已审核通过的签名，签名信息可登录
	 * [短信控制台](https://console.cloud.tencent.com/smsv2) 查看。注：国内短信为必填参数。
	 */
	private String sign;

	/**
	 * 国际/港澳台短信 senderid，国内短信填空，默认未开通，如需开通请联系 [sms
	 * helper](https://cloud.tencent.com/document/product/382/3773)。
	 */
	private String senderId;

	/**
	 * 短信码号扩展号，默认未开通，如需开通请联系 [sms
	 * helper](https://cloud.tencent.com/document/product/382/3773)。
	 */
	private String encode;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSdkappid() {
		return sdkappid;
	}

	public void setSdkappid(String sdkappid) {
		this.sdkappid = sdkappid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
}