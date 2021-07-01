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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.Joiner;
import com.tencentcloud.spring.boot.tim.TimApiAddress;

/**
 * This class gathers all the utilities methods.
 */
public final class CommonHelper {

	/**
	 * 一周秒数
	 */
	public static final Integer ONE_WEEK_SECOND = 7 * 24 * 60 * 60;

	private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	private static final String RTMP_PREFIX = "rtmp://";
	private static final String HTTP_PREFIX = "http://";
	private static final String FLV_SUFFIX = ".flv";
	private static final String HLS_SUFFIX = ".m3u8";
	private static final String PARAMETER_CONNECTOR = "?";

	public static final String DELIMITER = "&";
	public static final String SEPARATOR = "=";
	public static final Joiner.MapJoiner joiner = Joiner.on(DELIMITER).withKeyValueSeparator(SEPARATOR);
	
	public static String getMixStreamSessionId(final String streamName) {
		StringBuilder mixStreamSessionId = new StringBuilder(streamName);
		mixStreamSessionId.append("_").append(System.currentTimeMillis());
		return mixStreamSessionId.toString();
	}
	
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
	public static String getSafeUrl(String key, String streamName, long txTime) {
		
           String input = new StringBuilder().
                             append(key).
                             append(streamName).
                             append(Long.toHexString(txTime).toUpperCase()).toString();

           String txSecret = null;
           try {
                 MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                 txSecret  = byteArrayToHexString(
                             messageDigest.digest(input.getBytes("UTF-8")));
           } catch (NoSuchAlgorithmException e) {
                 e.printStackTrace();
           } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
           }

           return txSecret == null ? "" :
                             new StringBuilder().
                             append("txSecret=").
                             append(txSecret).
                             append("&").
                             append("txTime=").
                             append(Long.toHexString(txTime).toUpperCase()).
                             toString();
     }

     private static String byteArrayToHexString(byte[] data) {
           char[] out = new char[data.length << 1];

           for (int i = 0, j = 0; i < data.length; i++) {
                 out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
                 out[j++] = DIGITS_LOWER[0x0F & data[i]];
           }
           return new String(out);
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