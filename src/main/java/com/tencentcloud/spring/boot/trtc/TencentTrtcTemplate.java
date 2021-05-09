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
package com.tencentcloud.spring.boot.trtc;

import com.tencentcloud.spring.boot.TencentTrtcProperties;
import com.tencentcloudapi.trtc.v20190722.TrtcClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TencentTrtcTemplate {
	private static final String DELIMITER = "_";

	private TrtcClient trtcClient;
	private TencentTrtcProperties liveProperties;

	public TencentTrtcTemplate(TrtcClient trtcClient, TencentTrtcProperties liveProperties) {
		this.trtcClient = trtcClient;
		this.liveProperties = liveProperties;
	}
 
	
	
	/**
	 * 反向解析流名称获取userId
	 * @param streamName
	 * @return userId
	 */
	public String getUserIdByStreamName(String streamName) {
		String[] split = streamName.split(DELIMITER);
		if (split.length != 2) {
			throw new IllegalArgumentException ("反向解析流名称获取userId异常！");
		}
		return split[0];
	}
	
	/**
	 * 根据userId生成流名称
	 * @param userId
	 * @return 流名称
	 */
	public String getStreamNameByUserId(String userId) {
		StringBuilder streamName = new StringBuilder(userId).append(DELIMITER).append(System.currentTimeMillis());
		return streamName.toString();
	}

}
