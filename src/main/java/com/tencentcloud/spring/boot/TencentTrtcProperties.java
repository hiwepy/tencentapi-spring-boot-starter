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

@ConfigurationProperties(TencentTrtcProperties.PREFIX)
@Data
public class TencentTrtcProperties {

	public static final String PREFIX = "tencent.cloud.trtc";

	/**
	 * Enable Tencent Trtc .
	 */
	private boolean enabled = false;
	
	/**
    * TRTC的SDKAppId。
    */
    private Long sdkappid;
	    
	/**
	 * 官网获取的 API ID（腾讯云应用的AK）
	 */
	private String secretId;
	/**
	 * 官网获取的 Secret Key（腾讯应用的SK）
	 */
  	private String secretKey;

	private String region;
	
    private HttpProfile httpProfile = new HttpProfile();

    private String signMethod = ClientProfile.SIGN_TC3_256;
    
    /**
     * If payload is NOT involved in signing process, true means will ignore payload, default is
     * false.
     */
    private boolean unsignedPayload;

    /**
     * valid choices: zh-CN, en-US
     */
    private Language language = Language.ZH_CN;
    
  	private String pushDomain;
  	
    private String playDomain;
    
    private String appName;
    
    private Integer retryTimes = 2;
	
}
