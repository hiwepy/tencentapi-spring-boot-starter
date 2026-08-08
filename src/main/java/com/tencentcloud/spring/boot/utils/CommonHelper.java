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
 * Internal helper utilities for the Tencent Cloud starters: live-stream URL
 * building and signing, mix-stream session id generation, MD5 hex encoding and
 * TIM REST API URL composition.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
public final class CommonHelper {

	/** Number of seconds in one week; used as the validity window for generated stream URLs. */
	public static final Integer ONE_WEEK_SECOND = 7 * 24 * 60 * 60;

	private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	private static final String RTMP_PREFIX = "rtmp://";
	private static final String WEBRTC_PREFIX = "webrtc://";
	private static final String HTTP_PREFIX = "http://";
	private static final String FLV_SUFFIX = ".flv";
	private static final String HLS_SUFFIX = ".m3u8";
	private static final String URL_DELIMITER = "/";
	private static final String PARAMETER_CONNECTOR = "?";

	/** Separator used when joining query-string key/value pairs. */
	public static final String DELIMITER = "&";
	/** Separator between a query-string key and its value. */
	public static final String SEPARATOR = "=";
	/** Guava joiner for rendering a map as a query string. */
	public static final Joiner.MapJoiner joiner = Joiner.on(DELIMITER).withKeyValueSeparator(SEPARATOR);

	/**
	 * Builds a mix-stream session id by appending the current timestamp to the
	 * given stream name ({@code streamName_timestamp}).
	 *
	 * @param streamName the base stream name
	 * @return the generated mix-stream session id
	 */
	public static String getMixStreamSessionId(final String streamName) {
		StringBuilder mixStreamSessionId = new StringBuilder(streamName);
		mixStreamSessionId.append("_").append(System.currentTimeMillis());
		return mixStreamSessionId.toString();
	}

	/**
	 * Builds the RTMP push URL: {@code rtmp://domain/AppName/StreamName?txSecret=...&txTime=...}.
	 *
	 * @param pushDomain push (ingest) domain
	 * @param appName    app name path segment
	 * @param streamName stream name
	 * @param safeUrl    pre-computed authentication query string
	 * @return the RTMP push URL
	 */
	public static StringBuilder getRtmpUrl(final String pushDomain, String appName, String streamName, final String safeUrl) {
		StringBuilder rtmpUrl = new StringBuilder();
		rtmpUrl.append(RTMP_PREFIX).append(pushDomain).append(URL_DELIMITER).append(appName).append(URL_DELIMITER).append(streamName).append(PARAMETER_CONNECTOR).append(safeUrl);
		return rtmpUrl;
	}

	/**
	 * Builds the WebRTC push URL: {@code webrtc://domain/AppName/StreamName?txSecret=...&txTime=...}.
	 *
	 * @param pushDomain push (ingest) domain
	 * @param appName    app name path segment
	 * @param streamName stream name
	 * @param safeUrl    pre-computed authentication query string
	 * @return the WebRTC push URL
	 */
	public static StringBuilder getWebrtcUrl(final String pushDomain, String appName, String streamName, final String safeUrl) {
		StringBuilder rtmpUrl = new StringBuilder();
		rtmpUrl.append(WEBRTC_PREFIX).append(pushDomain).append(URL_DELIMITER).append(appName).append(URL_DELIMITER).append(streamName).append(PARAMETER_CONNECTOR).append(safeUrl);
		return rtmpUrl;
	}

	/**
	 * Builds the HTTP-FLV play URL: {@code http://domain/AppName/StreamName.flv?txSecret=...}.
	 *
	 * @param playDomain playback domain
	 * @param appName    app name path segment
	 * @param streamName stream name
	 * @param safeUrl    pre-computed authentication query string
	 * @return the HTTP-FLV play URL
	 */
	public static StringBuilder getFlvUrl(final String playDomain, String appName, String streamName, final String safeUrl) {
		StringBuilder flvUrl = new StringBuilder();
		flvUrl.append(HTTP_PREFIX).append(playDomain).append(URL_DELIMITER).append(appName).append(URL_DELIMITER).append(streamName).append(FLV_SUFFIX).append(PARAMETER_CONNECTOR).append(safeUrl);
		return flvUrl;
	}

	/**
	 * Builds the HLS play URL: {@code http://domain/AppName/StreamName.m3u8?txSecret=...}.
	 *
	 * @param playDomain playback domain
	 * @param appName    app name path segment
	 * @param streamName stream name
	 * @param safeUrl    pre-computed authentication query string
	 * @return the HLS play URL
	 */
	public static StringBuilder getHlsUrl(final String playDomain, String appName, String streamName, final String safeUrl) {
		StringBuilder hlsUrl = new StringBuilder();
		hlsUrl.append(HTTP_PREFIX).append(playDomain).append(URL_DELIMITER).append(appName).append(URL_DELIMITER).append(streamName).append(HLS_SUFFIX).append(PARAMETER_CONNECTOR).append(safeUrl);
		return hlsUrl;
	}

	/**
	 * Computes the Tencent Live authentication query string
	 * {@code txSecret=Md5(key+StreamName+hex(time))&txTime=hex(time)} for the
	 * given stream key, stream name and expiry timestamp.
	 *
	 * @param key        the stream authentication key
	 * @param streamName the stream name being signed
	 * @param txTime     expiry time in seconds since the epoch
	 * @return the {@code txSecret=...&txTime=...} query string, or empty on error
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

	/**
	 * Encodes a byte array as a lowercase hexadecimal string.
	 *
	 * @param data the bytes to encode
	 * @return the hex representation
	 */
     private static String byteArrayToHexString(byte[] data) {
           char[] out = new char[data.length << 1];

           for (int i = 0, j = 0; i < data.length; i++) {
                 out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
                 out[j++] = DIGITS_LOWER[0x0F & data[i]];
           }
           return new String(out);
     }


	/**
	 * Composes a fully-qualified TIM REST API URL by appending the given
	 * query parameters to the {@link TimApiAddress} base URL, using {@code ?}
	 * or {@code &} as appropriate.
	 *
	 * @param address the TIM API address enum constant
	 * @param data    the query parameters to append (may be empty/null)
	 * @return the fully-qualified request URL
	 */
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