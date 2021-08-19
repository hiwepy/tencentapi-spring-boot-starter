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

import java.security.SecureRandom;

import org.springframework.util.StringUtils;

import com.tencentcloud.spring.boot.TencentLiveProperties;
import com.tencentcloud.spring.boot.live.resp.MixStreamResult;
import com.tencentcloud.spring.boot.live.resp.StreamResult;
import com.tencentcloud.spring.boot.utils.CommonHelper;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.cvm.v20170312.models.DescribeZonesRequest;
import com.tencentcloudapi.live.v20180801.LiveClient;
import com.tencentcloudapi.live.v20180801.models.CancelCommonMixStreamRequest;
import com.tencentcloudapi.live.v20180801.models.CancelCommonMixStreamResponse;
import com.tencentcloudapi.live.v20180801.models.CommonMixControlParams;
import com.tencentcloudapi.live.v20180801.models.CommonMixCropParams;
import com.tencentcloudapi.live.v20180801.models.CommonMixInputParam;
import com.tencentcloudapi.live.v20180801.models.CommonMixLayoutParams;
import com.tencentcloudapi.live.v20180801.models.CommonMixOutputParams;
import com.tencentcloudapi.live.v20180801.models.CreateCommonMixStreamRequest;
import com.tencentcloudapi.live.v20180801.models.CreateCommonMixStreamResponse;
import com.tencentcloudapi.live.v20180801.models.DescribeLiveStreamStateRequest;
import com.tencentcloudapi.live.v20180801.models.DescribeLiveStreamStateResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Live: 腾讯云直播
 * https://cloud.tencent.com/document/product/267/41299
 * https://cloud.tencent.com/document/product/267/32744
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@Slf4j
public class TencentLiveTemplate {
	
	private static SecureRandom srandom = new SecureRandom();
	/**
	 * 一周秒数
	 */
	public static final Integer ONE_WEEK_SECOND = 7 * 24 * 60 * 60;
	//private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static final String CANV_NAME = "canv";
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

	/**
	 * 根据用户ID生成推流地址
	 * @param userId 用户ID
	 * @return 流地址信息
	 */
	public StreamResult createStream(String userId) {
		return createStreamByStreamName(this.getStreamNameByUserId(userId));
	}

	/**
	 * 根据流名称生成推流地址
	 * @param streamName 流名称
	 * https://cloud.tencent.com/document/product/267/43392
	 * @return 流地址信息
	 */
	public StreamResult createStreamByStreamName(String streamName) {
		String secretUrl = CommonHelper.getSafeUrl(liveProperties.getStreamUrlKey(), streamName, System.currentTimeMillis() / 1000 + ONE_WEEK_SECOND);
		return StreamResult.builder().streamName(streamName)
				.rtmpUrl(CommonHelper.getRtmpUrl(liveProperties.getPushDomain(), liveProperties.getAppName(), streamName, secretUrl))
				.webrtcUrl(CommonHelper.getWebrtcUrl(liveProperties.getPushDomain(), liveProperties.getAppName(), streamName, secretUrl))
				.flvUrl(CommonHelper.getFlvUrl(liveProperties.getPlayDomain(), liveProperties.getAppName(), streamName, secretUrl))
				.hlsUrl(CommonHelper.getHlsUrl(liveProperties.getPlayDomain(), liveProperties.getAppName(), streamName, secretUrl))
				.build();
	}
	
	/**
	 * 2、创建通用混流
	 * API:https://cloud.tencent.com/document/product/267/43404
	 * @param homeStreamName 主场流名称
	 * @param awayStreamName 客场流名称
	 * @param retryTimes	   重试次数
	 * @return 混流结果
	 */
	public MixStreamResult createMixStream(String homeStreamName, String awayStreamName, int retryTimes) {
		
		// 输出设置
		CommonMixOutputParams outputParams = new CommonMixOutputParams();
		String outputStreamName = System.currentTimeMillis() + DELIMITER + srandom.nextInt(10000);
		outputParams.setOutputStreamName(outputStreamName);
		outputParams.setOutputStreamType(1L);
		
		CommonMixInputParam[] inputStreams = new CommonMixInputParam[3];
		
		// 画布设置
		CommonMixInputParam inputStream = new CommonMixInputParam();
		inputStream.setInputStreamName(CANV_NAME);
		CommonMixLayoutParams LayoutParams = new CommonMixLayoutParams();
		LayoutParams.setImageLayer(1L);
		LayoutParams.setInputType(3L);
		LayoutParams.setImageWidth(2 * WIDTH);
		LayoutParams.setImageHeight(HEIGHT);
		inputStream.setLayoutParams(LayoutParams);
		inputStreams[0] = inputStream;

		// 主场画面设置
		CommonMixInputParam inputStream1 = new CommonMixInputParam();
		inputStream1.setInputStreamName(homeStreamName);
		CommonMixLayoutParams layoutParams1 = new CommonMixLayoutParams();
		layoutParams1.setImageLayer(2L);
		layoutParams1.setImageWidth(WIDTH);
		layoutParams1.setImageHeight(HEIGHT);
		layoutParams1.setLocationX(DEFAULT_PARAM);
		layoutParams1.setLocationY(DEFAULT_PARAM);
		inputStream1.setLayoutParams(layoutParams1);

		CommonMixCropParams cropParams1 = new CommonMixCropParams();
		cropParams1.setCropWidth(WIDTH);
		cropParams1.setCropHeight(HEIGHT);
		inputStream1.setCropParams(cropParams1);
		inputStreams[1] = inputStream1;

		// 客场画面设置
		CommonMixInputParam inputStream2 = new CommonMixInputParam();
		inputStream2.setInputStreamName(awayStreamName);
		CommonMixLayoutParams layoutParams2 = new CommonMixLayoutParams();
		layoutParams2.setImageLayer(3L);
		layoutParams2.setImageWidth(WIDTH);
		layoutParams2.setImageHeight(HEIGHT);
		layoutParams2.setLocationX(WIDTH);
		layoutParams2.setLocationY(DEFAULT_PARAM);
		inputStream2.setLayoutParams(layoutParams2);
		CommonMixCropParams cropParams2 = new CommonMixCropParams();
		cropParams2.setCropWidth(WIDTH);
		cropParams2.setCropHeight(HEIGHT);
		inputStream2.setCropParams(cropParams2);
		inputStreams[2] = inputStream2;
		
		return this.createMixStream(homeStreamName, awayStreamName, retryTimes, inputStreams, outputParams);
		
	}
	
	/**
	 * 2、创建通用混流
	 * API:https://cloud.tencent.com/document/product/267/43404
	 * @param homeStreamName 主场流名称
	 * @param awayStreamName 客场流名称
	 * @param retryTimes	   重试次数
	 * @param inputStreams	   混流输入流列表
	 * @param outputParams	   混流输出流参数
	 * @return 混流结果
	 */
	public MixStreamResult createMixStream(String homeStreamName, String awayStreamName, int retryTimes, 
			CommonMixInputParam[] inputStreams,
			CommonMixOutputParams outputParams) {
		MixStreamResult result = null;
		boolean isSuccess;
		do {
			try {
				
				CreateCommonMixStreamRequest req = new CreateCommonMixStreamRequest();

				// 混流会话（申请混流开始到取消混流结束）标识 ID。 该值与CreateCommonMixStream中的MixStreamSessionId保持一致。
				
				String mixStreamSessionId = CommonHelper.getMixStreamSessionId(homeStreamName);
				
				req.setInputStreamList(inputStreams);
				req.setMixStreamSessionId(mixStreamSessionId);
				req.setOutputParams(outputParams);

				// 通过client对象调用想要访问的接口，需要传入请求对象
				CreateCommonMixStreamResponse commonMixStreamResponse = liveClient.CreateCommonMixStream(req);
				
				// 输出json格式的字符串回包
				log.info("混流成功  {} {}", mixStreamSessionId, DescribeZonesRequest.toJsonString(commonMixStreamResponse));

				result = MixStreamResult.builder()
						.sessionId(mixStreamSessionId.toString())
						.streamName(outputParams.getOutputStreamName()).build();
			} catch (TencentCloudSDKException e) {
				log.error("{}混流异常", homeStreamName, e);
			}
			
			isSuccess = StringUtils.hasText(result.getSessionId()) && ++retryTimes < 2;
		} while (isSuccess);

		StreamResult stream = this.createStreamByStreamName(result.getStreamName());
		result.setRtmpUrl(stream.getRtmpUrl());
		result.setWebrtcUrl(stream.getWebrtcUrl());
		result.setHlsUrl(stream.getHlsUrl());
		result.setFlvUrl(stream.getFlvUrl());
		
		return result;
	}
	
	/**
	 * 2、创建通用混流
	 * API:https://cloud.tencent.com/document/product/267/43404
	 * @param homeStreamName 主场流名称
	 * @param awayStreamName 客场流名称
	 * @param retryTimes	   重试次数
	 * @param controlParams  混流的特殊控制参数
	 * @param inputStreams	   混流输入流列表
	 * @param outputParams	   混流输出流参数
	 * @return 混流结果
	 */
	public MixStreamResult createMixStream(String homeStreamName, String awayStreamName, int retryTimes, 
			CommonMixControlParams controlParams,
			CommonMixInputParam[] inputStreams,
			CommonMixOutputParams outputParams) {
		MixStreamResult result = null;
		boolean isSuccess;
		do {
			try {
				
				CreateCommonMixStreamRequest req = new CreateCommonMixStreamRequest();

				// 混流会话（申请混流开始到取消混流结束）标识 ID。 该值与CreateCommonMixStream中的MixStreamSessionId保持一致。
				
				String mixStreamSessionId = CommonHelper.getMixStreamSessionId(homeStreamName);
				
				req.setControlParams(controlParams);
				req.setInputStreamList(inputStreams);
				req.setMixStreamSessionId(mixStreamSessionId);
				req.setOutputParams(outputParams);

				// 通过client对象调用想要访问的接口，需要传入请求对象
				CreateCommonMixStreamResponse commonMixStreamResponse = liveClient.CreateCommonMixStream(req);
				
				// 输出json格式的字符串回包
				log.info("混流成功  {} {}", mixStreamSessionId, DescribeZonesRequest.toJsonString(commonMixStreamResponse));

				result = MixStreamResult.builder()
						.sessionId(mixStreamSessionId.toString())
						.streamName(outputParams.getOutputStreamName()).build();
			} catch (TencentCloudSDKException e) {
				log.error("{}混流异常", homeStreamName, e);
			}
			
			isSuccess = StringUtils.hasText(result.getSessionId()) && ++retryTimes < 2;
		} while (isSuccess);

		StreamResult stream = this.createStreamByStreamName(result.getStreamName());
		result.setRtmpUrl(stream.getRtmpUrl());
		result.setWebrtcUrl(stream.getWebrtcUrl());
		result.setHlsUrl(stream.getHlsUrl());
		result.setFlvUrl(stream.getFlvUrl());
		
		return result;
	}
	
	/**
	 * 3、取消通用混流
	 * API:https://cloud.tencent.com/document/product/267/43405
	 * @param mixStreamSessionId 混流session id
	 * @param  retryTimes 重试次数
	 * @return 是否取消混流成功
	 */
	public boolean stopMixStream(String mixStreamSessionId, int retryTimes) {
		boolean isSuccess = Boolean.FALSE;
		do {
			isSuccess = this.stopMixStream(mixStreamSessionId);
			isSuccess = !isSuccess && ++retryTimes < liveProperties.getRetryTimes();
		} while (isSuccess);
		return isSuccess;
	}
	
	/**
	 * 取消混流
	 * @param mixStreamSessionId 混流session id
	 * @return 是否取消混流成功
	 */
	public boolean stopMixStream(String mixStreamSessionId) {
		if (StringUtils.hasText(mixStreamSessionId)) {
			try {
				CancelCommonMixStreamRequest req = new CancelCommonMixStreamRequest();
				req.setMixStreamSessionId(mixStreamSessionId);
				// 通过client对象调用想要访问的接口，需要传入请求对象
				CancelCommonMixStreamResponse commonMixStreamResponse = liveClient.CancelCommonMixStream(req);
				// 输出json格式的字符串回包
				log.info(DescribeZonesRequest.toJsonString(commonMixStreamResponse));
				return Boolean.TRUE;
			} catch (TencentCloudSDKException e) {
				log.error("{}取消混流异常", mixStreamSessionId, e);
				return Boolean.FALSE;
			}
		}
		return Boolean.FALSE;
	}
	
	/**
     * 4、查询视频流状态
     * @return     流状态，active：活跃，inactive：非活跃，forbid：禁播。
     * @throws TencentCloudSDKException SDK异常
     */
    public String describeLiveStreamState(String streamName) throws TencentCloudSDKException {
        LiveClient liveClient = getLiveClient();
        DescribeLiveStreamStateRequest req = new DescribeLiveStreamStateRequest();
        req.setAppName(getLiveProperties().getAppName());
        req.setDomainName(getLiveProperties().getPushDomain());
        req.setStreamName(streamName);
        DescribeLiveStreamStateResponse resp = liveClient.DescribeLiveStreamState(req);
        log.info("查看视频流状态result:{}", DescribeLiveStreamStateResponse.toJsonString(resp));
        return resp.getStreamState();
    }
    
	/**
	 * 反向解析流名称获取userId
	 * @param streamName 流名称
	 * @return userId 用户ID
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
	 * @param userId 用户ID
	 * @return 流名称
	 */
	public String getStreamNameByUserId(String userId) {
		StringBuilder streamName = new StringBuilder(userId).append(DELIMITER).append(System.currentTimeMillis());
		return streamName.toString();
	}
	
	public LiveClient getLiveClient() {
		return liveClient;
	}
	
	public TencentLiveProperties getLiveProperties() {
		return liveProperties;
	}

}
