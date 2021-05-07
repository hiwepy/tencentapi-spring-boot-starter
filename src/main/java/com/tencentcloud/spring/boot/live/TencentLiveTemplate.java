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
package com.tencentcloud.spring.boot.live;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.util.StringUtils;

import com.tencentcloud.spring.boot.TencentLiveProperties;
import com.tencentcloud.spring.boot.live.resp.MixStreamResult;
import com.tencentcloud.spring.boot.live.resp.StreamResult;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.cvm.v20170312.models.DescribeZonesRequest;
import com.tencentcloudapi.live.v20180801.LiveClient;
import com.tencentcloudapi.live.v20180801.models.CancelCommonMixStreamRequest;
import com.tencentcloudapi.live.v20180801.models.CancelCommonMixStreamResponse;
import com.tencentcloudapi.live.v20180801.models.CommonMixCropParams;
import com.tencentcloudapi.live.v20180801.models.CommonMixInputParam;
import com.tencentcloudapi.live.v20180801.models.CommonMixLayoutParams;
import com.tencentcloudapi.live.v20180801.models.CommonMixOutputParams;
import com.tencentcloudapi.live.v20180801.models.CreateCommonMixStreamRequest;
import com.tencentcloudapi.live.v20180801.models.CreateCommonMixStreamResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TencentLiveTemplate {
	
	private static SecureRandom srandom = new SecureRandom();
	/**
	 * 一周秒数
	 */
	public static final Integer ONE_WEEK_SECOND = 7 * 24 * 60 * 60;
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	private static final String CANV_NAME = "canv";
	private static final String RTMP_PREFIX = "rtmp://";
	private static final String HTTP_PREFIX = "http://";
	private static final String FLV_SUFFIX = ".flv";
	private static final String HLS_SUFFIX = ".m3u8";
	private static final String PARAMETER_CONNECTOR = "?";
	private static final String DELIMITER = "_";

	private static final Float DEFAULT_PARAM = 0F;
	private static final Float WIDTH = 368F;
	private static final Float HEIGHT = 640F;

	private LiveClient liveClient;
	private TencentLiveProperties liveProperties;

	public TencentLiveTemplate(LiveClient liveClient, TencentLiveProperties liveProperties) {
		this.liveClient = liveClient;
		this.liveProperties = liveProperties;
	}

	/*
	 * KEY+ streamName + txTime
	 */
	private String getSafeUrl(String key, String streamName, long txTime) {
		String input = new StringBuilder().append(key).append(streamName).append(Long.toHexString(txTime).toUpperCase())
				.toString();
		String txSecret = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			txSecret = byteArrayToHexString(messageDigest.digest(input.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return txSecret == null ? ""
				: new StringBuilder().append("txSecret=").append(txSecret).append("&").append("txTime=")
						.append(Long.toHexString(txTime).toUpperCase()).toString();
	}

	private String byteArrayToHexString(byte[] data) {
		char[] out = new char[data.length << 1];
		for (int i = 0, j = 0; i < data.length; i++) {
			out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS_LOWER[0x0F & data[i]];
		}
		return new String(out);
	}

	/**
	 * 生成推流地址
	 * 
	 * @param userId
	 * @return
	 */
	public StreamResult generateStreamUrlByUserId(String userId) {
		StringBuilder streamName = new StringBuilder();
		streamName.append(userId).append(DELIMITER).append(System.currentTimeMillis());
		return generateStreamUrlByStreamName(streamName.toString());
	}

	public StreamResult generateStreamUrlByStreamName(String streamName) {
		StringBuilder pushUrl = new StringBuilder();
		pushUrl.append(liveProperties.getPushDomain()).append(liveProperties.getAppName());
		StringBuilder playUrl = new StringBuilder();
		playUrl.append(liveProperties.getPlayDomain()).append(liveProperties.getAppName());
		String safeUrl = getSafeUrl(liveProperties.getTencentStreamUrlKey(), streamName,
				System.currentTimeMillis() / 1000 + ONE_WEEK_SECOND);
		StreamResult streamUrl = new StreamResult();
		streamUrl.getRtmpUrl().append(RTMP_PREFIX).append(pushUrl).append(streamName).append(PARAMETER_CONNECTOR)
				.append(safeUrl);
		streamUrl.getFlvUrl().append(HTTP_PREFIX).append(playUrl).append(streamName).append(FLV_SUFFIX)
				.append(PARAMETER_CONNECTOR).append(safeUrl);
		streamUrl.getHlsUrl().append(HTTP_PREFIX).append(playUrl).append(streamName).append(HLS_SUFFIX)
				.append(PARAMETER_CONNECTOR).append(safeUrl);
		streamUrl.setStreamName(streamName);
		return streamUrl;
	}

	public MixStreamResult createMixStream(String homeStreamName, String customStreamName, int retryTimes) {
		MixStreamResult result = new MixStreamResult();
		boolean isSuccess;
		do {
			try {
				result = this.createMixStream(homeStreamName, customStreamName);
			} catch (TencentCloudSDKException e) {
				log.error("{}混流异常", homeStreamName, e);
			}
			isSuccess = org.apache.commons.lang3.StringUtils.isBlank(result.getSessionId()) && ++retryTimes < 2;
		} while (isSuccess);

		StreamResult streamUrl = this.generateStreamUrlByStreamName(result.getStreamName());
		result.setHlsUrl(streamUrl.getHlsUrl());
		result.setRtmpUrl(streamUrl.getRtmpUrl());
		result.setFlvUrl(streamUrl.getFlvUrl());

		return result;
	}
	
	public void cancelMixStream(String mixStreamSessionId, int retryTimes) {
		boolean isSuccess = Boolean.FALSE;
		do {
			try {
				this.cancelMixStream(mixStreamSessionId);
				isSuccess = Boolean.TRUE;
			} catch (TencentCloudSDKException e) {
				log.error("{}取消混流异常", mixStreamSessionId, e);
			}
			isSuccess = !isSuccess && ++retryTimes < 2;
		} while (isSuccess);
	}

	/**
	 * 创建混流
	 * 
	 * @param homeStreamName   主场流名称
	 * @param customStreamName 客场流名称
	 * @return 混流的 session id
	 * @throws TencentCloudSDKException
	 */
	private MixStreamResult createMixStream(String homeStreamName, String customStreamName) throws TencentCloudSDKException {
		
		MixStreamResult result = new MixStreamResult();
		
		CreateCommonMixStreamRequest req = new CreateCommonMixStreamRequest();

		StringBuilder mixStreamSessionId = new StringBuilder(homeStreamName);
		mixStreamSessionId.append("_").append(System.currentTimeMillis());
		result.setSessionId(mixStreamSessionId.toString());
		req.setMixStreamSessionId(mixStreamSessionId.toString());
		// 输出设置
		CommonMixOutputParams OutputParams = new CommonMixOutputParams();
		String outputStreamName = System.currentTimeMillis() + DELIMITER + srandom.nextInt(10000);
		result.setStreamName(outputStreamName);
		OutputParams.setOutputStreamName(outputStreamName);
		OutputParams.setOutputStreamType(1L);
		req.setOutputParams(OutputParams);

		CommonMixInputParam[] InputStreamList = new CommonMixInputParam[3];
		// 画布设置
		CommonMixInputParam inputStream = new CommonMixInputParam();
		inputStream.setInputStreamName(CANV_NAME);
		CommonMixLayoutParams LayoutParams = new CommonMixLayoutParams();
		LayoutParams.setImageLayer(1L);
		LayoutParams.setInputType(3L);
		LayoutParams.setImageWidth(2 * WIDTH);
		LayoutParams.setImageHeight(HEIGHT);
		inputStream.setLayoutParams(LayoutParams);
		InputStreamList[0] = inputStream;

		// 主场画面设置
		CommonMixInputParam inputStream1 = new CommonMixInputParam();
		inputStream1.setInputStreamName(homeStreamName);
		CommonMixLayoutParams LayoutParams1 = new CommonMixLayoutParams();
		LayoutParams1.setImageLayer(2L);
		LayoutParams1.setImageWidth(WIDTH);
		LayoutParams1.setImageHeight(HEIGHT);
		LayoutParams1.setLocationX(DEFAULT_PARAM);
		LayoutParams1.setLocationY(DEFAULT_PARAM);
		inputStream1.setLayoutParams(LayoutParams1);

		CommonMixCropParams cropParams1 = new CommonMixCropParams();
		cropParams1.setCropWidth(WIDTH);
		cropParams1.setCropHeight(HEIGHT);
		inputStream1.setCropParams(cropParams1);
		InputStreamList[1] = inputStream1;

		// 客场画面设置
		CommonMixInputParam inputStream2 = new CommonMixInputParam();
		inputStream2.setInputStreamName(customStreamName);
		CommonMixLayoutParams LayoutParams2 = new CommonMixLayoutParams();
		LayoutParams2.setImageLayer(3L);
		LayoutParams2.setImageWidth(WIDTH);
		LayoutParams2.setImageHeight(HEIGHT);
		LayoutParams2.setLocationX(WIDTH);
		LayoutParams2.setLocationY(DEFAULT_PARAM);
		inputStream2.setLayoutParams(LayoutParams2);
		CommonMixCropParams cropParams2 = new CommonMixCropParams();
		cropParams2.setCropWidth(WIDTH);
		cropParams2.setCropHeight(HEIGHT);
		inputStream2.setCropParams(cropParams2);
		InputStreamList[2] = inputStream2;

		req.setInputStreamList(InputStreamList);

		// 通过client对象调用想要访问的接口，需要传入请求对象
		CreateCommonMixStreamResponse createCommonMixStreamResponse = liveClient.CreateCommonMixStream(req);
		
		// 输出json格式的字符串回包
		log.info("混流成功  {} {}", mixStreamSessionId, DescribeZonesRequest.toJsonString(createCommonMixStreamResponse));

		return result;
	}

	/**
	 * 取消混流
	 * 
	 * @param mixStreamSeesionId 混流session id
	 * @throws TencentCloudSDKException
	 */
	public void cancelMixStream(String mixStreamSeesionId) throws TencentCloudSDKException {

		if (!StringUtils.hasText(mixStreamSeesionId)) {
			return;
		}
		
		CancelCommonMixStreamRequest req = new CancelCommonMixStreamRequest();
		req.setMixStreamSessionId(mixStreamSeesionId);

		// 通过client对象调用想要访问的接口，需要传入请求对象
		CancelCommonMixStreamResponse cancelCommonMixStreamResponse = liveClient.CancelCommonMixStream(req);

		// 输出json格式的字符串回包
		System.out.println(DescribeZonesRequest.toJsonString(cancelCommonMixStreamResponse));
	}

	/**
	 * 反向解析流名称获取userId
	 * @param streamName
	 * @return
	 */
	public static Long getUserIdByStreamName(String streamName) {
		String[] split = streamName.split(DELIMITER);
		if (split.length != 2) {
			
		}
		return Long.parseLong(split[0]);
	}

}
