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

@ConfigurationProperties(TencentCloudProperties.PREFIX)
@Data
public class TencentCloudProperties {

	public static final String PREFIX = "tencent.cloud";
	
	/**
	 * 官网获取的 API ID（腾讯云应用的AK）
	 */
	private String secretId;
	/**
	 * 官网获取的 Secret Key（腾讯应用的SK）
	 */
  	private String secretKey;
    
    private boolean debug;
	
}
