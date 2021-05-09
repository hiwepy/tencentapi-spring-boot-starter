/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
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
package com.tencentcloud.spring.boot.utils;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.DigestUtils;

import com.google.common.base.Joiner;
import com.tencentcloud.spring.boot.tim.TimApiAddress;

/**
 * This class gathers all the utilities methods.
 */
public final class CommonHelper {

	private static SecureRandom srandom = new SecureRandom();
	/**
	 * 一周秒数
	 */
	public static final Integer ONE_WEEK_SECOND = 7 * 24 * 60 * 60;

	private static final String CANV_NAME = "canv";
	private static final String RTMP_PREFIX = "rtmp://";
	private static final String HTTP_PREFIX = "http://";
	private static final String FLV_SUFFIX = ".flv";
	private static final String HLS_SUFFIX = ".m3u8";
	private static final String PARAMETER_CONNECTOR = "?";
	private static final String DELIMITER1 = "_";

	private static final Float DEFAULT_PARAM = 0F;
	private static final Float WIDTH = 368F;
	private static final Float HEIGHT = 640F;

	public static final String DELIMITER = "&";
	public static final String SEPARATOR = "=";
	public static final Joiner.MapJoiner joiner = Joiner.on(DELIMITER).withKeyValueSeparator(SEPARATOR);
	
	/**
	 * 格式rtmp://domain/AppName/StreamName?txSecret=
	 */
	public static String getRtmpUrl(final String pushDomain, String appName, String streamName, final String safeUrl) {
		StringBuilder rtmpUrl = new StringBuilder();
		rtmpUrl.append(RTMP_PREFIX).append(pushDomain).append(appName).append(streamName).append(PARAMETER_CONNECTOR).append(safeUrl);
		return rtmpUrl.toString();
	}
	
	/**
	 * 格式http://domain/AppName/StreamName.flv?txSecret=
	 */
	public static String getFlvUrl(final String playDomain, String appName, String streamName, final String safeUrl) {
		StringBuilder flvUrl = new StringBuilder();
		flvUrl.append(HTTP_PREFIX).append(playDomain).append(appName).append(streamName).append(FLV_SUFFIX).append(PARAMETER_CONNECTOR).append(safeUrl);
		return flvUrl.toString();
	}
	
	/**
	 * 格式http://domain/AppName/StreamName.m3u8
	 */
	public static String getHlsUrl(final String playDomain, String appName, String streamName, final String safeUrl) {
		StringBuilder hlsUrl = new StringBuilder();
		hlsUrl.append(HTTP_PREFIX).append(playDomain).append(appName).append(streamName).append(HLS_SUFFIX).append(PARAMETER_CONNECTOR).append(safeUrl);
		return hlsUrl.toString();
	}

	/*
	 * KEY+ streamName + txTime
	 */
	public static String getSecretUrl(String key, String streamName, long txTime) {
		String input = new StringBuilder().append(key).append(streamName).append(Long.toHexString(txTime).toUpperCase()).toString();
		String txSecret = null;
		try {
			txSecret = DigestUtils.md5DigestAsHex(input.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return txSecret == null ? ""
				: new StringBuilder().append("txSecret=").append(txSecret).append("&").append("txTime=")
						.append(Long.toHexString(txTime).toUpperCase()).toString();
	}
	
	public static String getRequestUrl(final TimApiAddress address, final Map<String, String> data) {
		if (Objects.nonNull(data) && !data.isEmpty()) {
			final StringBuilder sb = new StringBuilder();
			sb.append(address.getUrl());
			if (address.getUrl().indexOf("?") >= 0) {
				sb.append("&");
			} else {
				sb.append("?");
			}
			sb.append(joiner.join(data));
			return sb.toString();
		}
		return address.getUrl();
	}

}