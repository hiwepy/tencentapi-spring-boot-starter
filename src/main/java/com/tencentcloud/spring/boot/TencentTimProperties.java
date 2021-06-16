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

@ConfigurationProperties(TencentTimProperties.PREFIX)
@Data
public class TencentTimProperties {

	public static final String ADMINISTRATOR = "administrator";
	public static final String PREFIX = "tencent.cloud.tim";
	// 单位秒
	private static final long EXPIRE = 86400 * 30;
	
	/**
	 * Enable Tencent Tim.
	 */
	private boolean enabled = false;

	private String domain;
	
	/**
	 * 帐号管理员
	 */
	private String identifier = ADMINISTRATOR;
	
	/**
	 * SDKAppID
	 */
	private Long sdkappid;
	
	/**
	 * 密钥
	 */
	private String privateKey;
	
	/**
	 * 签名过期时间，单位秒
	 */
	private long expire = EXPIRE;

	/**
	 * 消息离线保存时长（单位：秒），最长为7天（604800秒）
	 * 若设置该字段为0，则消息只发在线用户，不保存离线
	 * 若设置该字段超过7天（604800秒），仍只保存7天
	 * 若不设置该字段，则默认保存7天
	 */
	private long msgLifeTime = 604800;

}
