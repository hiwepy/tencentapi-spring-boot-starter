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
package com.baidu.ai.aip.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(FaceRecognitionV2Properties.PREFIX)
public class FaceRecognitionV2Properties {

	public static final String PREFIX = "baidu.face.v2";

	/**
	 * 	Enable Baidu Face Recognition.
	 */
	private boolean enabled = false;
	/**
	 * 	官网获取的 API Key（百度云应用的AK）
	 */
	private String clientId;
	/**
	 * 	官网获取的 Secret Key（百度云应用的SK）
	 */
	private String clientSecret;
	/**
	 * 最多处理人脸的数目，默认值为1，仅检测图片中面积最大的那个人脸
	 */
	private int maxFaceNum = 1;
	/**
	 * 包括age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities信息，逗号分隔，默认只返回人脸框、概率和旋转角度
	 */
	private String faceFields = "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities";
	/**
	 * 识别后返回的用户top数，默认为1，最多返回5个
	 */
	private int userTopNum = 1;
	/**
	 * 单帧图片活体检测阈值，取值范围[0~1]，大于阈值即可判断为活体
	 */
	private	double faceliveness = 0.834963;
	

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public int getMaxFaceNum() {
		return maxFaceNum;
	}

	public void setMaxFaceNum(int maxFaceNum) {
		this.maxFaceNum = maxFaceNum;
	}

	public String getFaceFields() {
		return faceFields;
	}

	public void setFaceFields(String faceFields) {
		this.faceFields = faceFields;
	}

	public int getUserTopNum() {
		return userTopNum;
	}

	public void setUserTopNum(int userTopNum) {
		this.userTopNum = userTopNum;
	}

	public double getFaceliveness() {
		return faceliveness;
	}

	public void setFaceliveness(double faceliveness) {
		this.faceliveness = faceliveness;
	}

}
