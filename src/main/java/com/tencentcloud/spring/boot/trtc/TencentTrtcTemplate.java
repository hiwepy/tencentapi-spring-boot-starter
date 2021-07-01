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

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import com.tencentcloud.spring.boot.TencentTrtcProperties;
import com.tencentcloud.spring.boot.utils.CommonHelper;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.trtc.v20190722.TrtcClient;
import com.tencentcloudapi.trtc.v20190722.models.DismissRoomByStrRoomIdRequest;
import com.tencentcloudapi.trtc.v20190722.models.DismissRoomByStrRoomIdResponse;
import com.tencentcloudapi.trtc.v20190722.models.DismissRoomRequest;
import com.tencentcloudapi.trtc.v20190722.models.DismissRoomResponse;
import com.tencentcloudapi.trtc.v20190722.models.EncodeParams;
import com.tencentcloudapi.trtc.v20190722.models.LayoutParams;
import com.tencentcloudapi.trtc.v20190722.models.OutputParams;
import com.tencentcloudapi.trtc.v20190722.models.PublishCdnParams;
import com.tencentcloudapi.trtc.v20190722.models.RemoveUserByStrRoomIdRequest;
import com.tencentcloudapi.trtc.v20190722.models.RemoveUserByStrRoomIdResponse;
import com.tencentcloudapi.trtc.v20190722.models.RemoveUserRequest;
import com.tencentcloudapi.trtc.v20190722.models.RemoveUserResponse;
import com.tencentcloudapi.trtc.v20190722.models.StartMCUMixTranscodeByStrRoomIdRequest;
import com.tencentcloudapi.trtc.v20190722.models.StartMCUMixTranscodeByStrRoomIdResponse;
import com.tencentcloudapi.trtc.v20190722.models.StartMCUMixTranscodeRequest;
import com.tencentcloudapi.trtc.v20190722.models.StartMCUMixTranscodeResponse;
import com.tencentcloudapi.trtc.v20190722.models.StopMCUMixTranscodeByStrRoomIdRequest;
import com.tencentcloudapi.trtc.v20190722.models.StopMCUMixTranscodeByStrRoomIdResponse;
import com.tencentcloudapi.trtc.v20190722.models.StopMCUMixTranscodeRequest;
import com.tencentcloudapi.trtc.v20190722.models.StopMCUMixTranscodeResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TencentTrtcTemplate {
	private static final String DELIMITER = "_";

	private TrtcUserIdProvider trtcUserIdProvider;
	private TrtcClient trtcClient;
	private TencentTrtcProperties trtcProperties;

	public TencentTrtcTemplate(TrtcClient trtcClient, TencentTrtcProperties trtcProperties,
			TrtcUserIdProvider trtcUserIdProvider) {
		this.trtcClient = trtcClient;
		this.trtcProperties = trtcProperties;
		this.trtcUserIdProvider = trtcUserIdProvider;
	}

	/**
	 * 1、将用户从房间移出，适用于主播/房主/管理员踢人等场景
	 * API：https://cloud.tencent.com/document/api/647/40496
	 * 
	 * @param roomId  房间ID
	 * @param userIds 业务用户ID数组
	 * @return 操作结果
	 * @throws TencentCloudSDKException
	 */
	public String kickout(Long roomId, String... userIds) throws TencentCloudSDKException {

		RemoveUserRequest req = new RemoveUserRequest();
		req.setSdkAppId(trtcProperties.getSdkappid());
		req.setRoomId(roomId);
		req.setUserIds(Stream.of(userIds).map(userId -> this.getTrtcUserByUserId(userId)).collect(Collectors.toList())
				.toArray(new String[userIds.length]));

		RemoveUserResponse resp = trtcClient.RemoveUser(req);

		String respString = RemoveUserResponse.toJsonString(resp);

		if (log.isDebugEnabled()) {
			log.debug("kickout user [{}] from Room {}, Response : {}", userIds, roomId, respString);
		}

		return respString;
	}

	/**
	 * 2、将用户从房间移出，适用于主播/房主/管理员踢人等场景
	 * API：https://cloud.tencent.com/document/api/647/50426
	 * 
	 * @param roomId  房间ID
	 * @param userIds 业务用户ID数组
	 * @return 操作结果
	 * @throws TencentCloudSDKException
	 */
	public String kickout(String roomId, String... userIds) throws TencentCloudSDKException {

		RemoveUserByStrRoomIdRequest req = new RemoveUserByStrRoomIdRequest();
		req.setSdkAppId(trtcProperties.getSdkappid());
		req.setRoomId(roomId);
		req.setUserIds(Stream.of(userIds).map(userId -> this.getTrtcUserByUserId(userId)).collect(Collectors.toList())
				.toArray(new String[userIds.length]));

		RemoveUserByStrRoomIdResponse resp = trtcClient.RemoveUserByStrRoomId(req);

		String respString = RemoveUserByStrRoomIdResponse.toJsonString(resp);

		if (log.isDebugEnabled()) {
			log.debug("kickout user [{}] from Room {}, Response : {}", userIds, roomId, respString);
		}

		return respString;
	}

	/**
	 * 3、解散房间，把房间所有用户从房间移出，解散房间 API：https://cloud.tencent.com/document/api/647/50089
	 * 
	 * @param roomId 房间ID
	 * @return 操作结果
	 * @throws TencentCloudSDKException
	 */
	public String dismissRoom(Long roomId) throws TencentCloudSDKException {

		DismissRoomRequest req = new DismissRoomRequest();
		req.setSdkAppId(trtcProperties.getSdkappid());
		req.setRoomId(roomId);

		DismissRoomResponse resp = trtcClient.DismissRoom(req);

		String respString = DismissRoomResponse.toJsonString(resp);

		if (log.isDebugEnabled()) {
			log.debug("Dismiss Room {}, Response : {}", roomId, respString);
		}

		return respString;
	}

	/**
	 * 2、解散房间（字符串房间号），把房间所有用户从房间移出，解散房间
	 * API：https://cloud.tencent.com/document/api/647/37088
	 * 
	 * @param roomId 房间ID
	 * @return 操作结果
	 * @throws TencentCloudSDKException
	 */
	public String dismissRoom(String roomId) throws TencentCloudSDKException {

		DismissRoomByStrRoomIdRequest req = new DismissRoomByStrRoomIdRequest();
		req.setSdkAppId(trtcProperties.getSdkappid());
		req.setRoomId(roomId);

		DismissRoomByStrRoomIdResponse resp = trtcClient.DismissRoomByStrRoomId(req);
		String respString = DismissRoomByStrRoomIdResponse.toJsonString(resp);

		if (log.isDebugEnabled()) {
			log.debug("Dismiss Room {}, Response : {}", roomId, respString);
		}

		return respString;
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author ： <a href="https://github.com/vindell">vindell</a>
	 * @param streamId        直播流 ID，由用户自定义设置，该流 ID 不能与用户旁路的流 ID 相同
	 * @param pureAudioStream 取值范围[0,1]， 填0：直播流为音视频(默认); 填1：直播流为纯音频
	 * @param recordId        自定义录制文件名称前缀。请先在实时音视频控制台开通录制功能，https://cloud.tencent.com/document/product/647/50768
	 * @param recordAudioOnly 取值范围[0,1]，填0无实际含义;
	 *                        填1：指定录制文件格式为mp3。此参数不建议使用，建议在实时音视频控制台配置纯音频录制模板。
	 * @return
	 */
	public OutputParams outputParams(String streamId, Long pureAudioStream, String recordId, Long recordAudioOnly) {

		// 混流会话（申请混流开始到结束流混流结束）标识 ID。 该值与CreateCommonMixStream中的MixStreamSessionId保持一致。
		String mixStreamSessionId = CommonHelper.getMixStreamSessionId(streamId);

		OutputParams outputParams1 = new OutputParams();
		outputParams1.setStreamId(mixStreamSessionId);
		outputParams1.setPureAudioStream(pureAudioStream);
		outputParams1.setRecordId(recordId);
		outputParams1.setRecordAudioOnly(recordAudioOnly);

		return outputParams1;

	}

	/**
	 * 2、启动云端混流，并指定混流画面中各路画面的布局位置。
	 * API:https://cloud.tencent.com/document/api/647/44270
	 * 
	 * @param roomId           房间ID
	 * @param outputParams     混流输出控制参数：https://cloud.tencent.com/document/api/647/44055#OutputParams
	 * @param encodeParams     混流输出编码参数：https://cloud.tencent.com/document/api/647/44055#EncodeParams
	 * @param layoutParams     混流输出布局参数：https://cloud.tencent.com/document/api/647/44055#LayoutParams
	 * @param publishCdnParams 第三方CDN转推参数：https://cloud.tencent.com/document/api/647/44055#PublishCdnParams
	 * @return
	 * @throws TencentCloudSDKException
	 */
	public String createMixStream(Long roomId, OutputParams outputParams, EncodeParams encodeParams,
			LayoutParams layoutParams, PublishCdnParams publishCdnParams) throws TencentCloudSDKException {

		StartMCUMixTranscodeRequest req = new StartMCUMixTranscodeRequest();
		req.setSdkAppId(trtcProperties.getSdkappid());
		req.setRoomId(roomId);

		req.setOutputParams(outputParams);
		req.setEncodeParams(encodeParams);
		req.setLayoutParams(layoutParams);
		req.setPublishCdnParams(publishCdnParams);

		StartMCUMixTranscodeResponse resp = trtcClient.StartMCUMixTranscode(req);

		String respString = StartMCUMixTranscodeResponse.toJsonString(resp);

		if (log.isInfoEnabled()) {
			log.info("Mix Stream Success! RoomId : {}, , StreamId : {},  Response : {}", roomId,
					outputParams.getStreamId(), respString);
		}

		return respString;

	}

	/**
	 * 2、启动云端混流（字符串房间号），并指定混流画面中各路画面的布局位置。
	 * API:https://cloud.tencent.com/document/api/647/50236
	 * 
	 * @param roomId           房间ID
	 * @param outputParams     混流输出控制参数：https://cloud.tencent.com/document/api/647/44055#OutputParams
	 * @param encodeParams     混流输出编码参数：https://cloud.tencent.com/document/api/647/44055#EncodeParams
	 * @param layoutParams     混流输出布局参数：https://cloud.tencent.com/document/api/647/44055#LayoutParams
	 * @param publishCdnParams 第三方CDN转推参数：https://cloud.tencent.com/document/api/647/44055#PublishCdnParams
	 * @return
	 * @throws TencentCloudSDKException
	 */
	public String createMixStream(String roomId, OutputParams outputParams, EncodeParams encodeParams,
			LayoutParams layoutParams, PublishCdnParams publishCdnParams) throws TencentCloudSDKException {

		StartMCUMixTranscodeByStrRoomIdRequest req = new StartMCUMixTranscodeByStrRoomIdRequest();
		req.setSdkAppId(trtcProperties.getSdkappid());
		req.setStrRoomId(roomId);

		req.setOutputParams(outputParams);
		req.setEncodeParams(encodeParams);
		req.setLayoutParams(layoutParams);
		req.setPublishCdnParams(publishCdnParams);

		StartMCUMixTranscodeByStrRoomIdResponse resp = trtcClient.StartMCUMixTranscodeByStrRoomId(req);

		String respString = StartMCUMixTranscodeByStrRoomIdResponse.toJsonString(resp);

		if (log.isInfoEnabled()) {
			log.info("Mix Stream Success! RoomId : {}, , StreamId : {},  Response : {}", roomId,
					outputParams.getStreamId(), respString);
		}

		return respString;

	}

	/**
	 * 3、结束云端混流 
	 * API:https://cloud.tencent.com/document/api/647/44269
	 * 
	 * @param roomId     roomId 房间ID
	 * @param retryTimes 重试次数
	 * @return 是否结束云端混流成功
	 */
	public boolean stopMixStream(Long roomId, int retryTimes) {
		boolean isSuccess = Boolean.FALSE;
		do {
			isSuccess = this.stopMixStream(roomId);
			isSuccess = !isSuccess && ++retryTimes < trtcProperties.getRetryTimes();
		} while (isSuccess);
		return isSuccess;
	}

	/**
	 * 4、结束云端混流 
	 * API:https://cloud.tencent.com/document/api/647/44269
	 * 
	 * @param roomId roomId 房间ID
	 * @return 是否结束云端混流成功
	 */
	public boolean stopMixStream(Long roomId) {
		if (Objects.nonNull(roomId)) {
			try {
				StopMCUMixTranscodeRequest req = new StopMCUMixTranscodeRequest();
				req.setSdkAppId(trtcProperties.getSdkappid());
				req.setRoomId(roomId);
				StopMCUMixTranscodeResponse resp = trtcClient.StopMCUMixTranscode(req);

				String respString = StopMCUMixTranscodeResponse.toJsonString(resp);

				if (log.isInfoEnabled()) {
					log.info("StopMix Stream Success! RoomId : {}, Response : {}", roomId, respString);
				}
				return Boolean.TRUE;
			} catch (TencentCloudSDKException e) {
				log.error("StopMix Stream Error ", e);
				return Boolean.FALSE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * 3、结束云端混流（字符串房间号） 
	 * API:https://cloud.tencent.com/document/api/647/50235
	 * 
	 * @param roomId     roomId 房间ID
	 * @param retryTimes 重试次数
	 * @return 是否结束云端混流成功
	 */
	public boolean stopMixStream(String roomId, int retryTimes) {
		boolean isSuccess = Boolean.FALSE;
		do {
			isSuccess = this.stopMixStream(roomId);
			isSuccess = !isSuccess && ++retryTimes < trtcProperties.getRetryTimes();
		} while (isSuccess);
		return isSuccess;
	}

	/**
	 * 3、结束云端混流（字符串房间号）
	 * API:https://cloud.tencent.com/document/api/647/50235
	 * 
	 * @param roomId roomId 房间ID
	 * @return 是否结束云端混流成功
	 */
	public boolean stopMixStream(String roomId) {
		if (StringUtils.hasText(roomId)) {
			try {
				StopMCUMixTranscodeByStrRoomIdRequest req = new StopMCUMixTranscodeByStrRoomIdRequest();
				req.setSdkAppId(trtcProperties.getSdkappid());
				req.setStrRoomId(roomId);
				StopMCUMixTranscodeByStrRoomIdResponse resp = trtcClient.StopMCUMixTranscodeByStrRoomId(req);

				String respString = StopMCUMixTranscodeByStrRoomIdResponse.toJsonString(resp);

				if (log.isInfoEnabled()) {
					log.info("StopMix Stream Success! RoomId : {}, Response : {}", roomId, respString);
				}
				return Boolean.TRUE;

			} catch (TencentCloudSDKException e) {
				log.error("StopMix Stream Error ", e);
				return Boolean.FALSE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * 反向解析流名称获取userId
	 * 
	 * @param streamId
	 * @return userId
	 */
	public String getUserIdByStreamName(String streamId) {
		String[] split = streamId.split(DELIMITER);
		if (split.length != 2) {
			throw new IllegalArgumentException("反向解析流名称获取userId异常！");
		}
		return split[0];
	}

	/**
	 * 根据userId生成流名称
	 * 
	 * @param userId
	 * @return 流名称
	 */
	public String getStreamNameByUserId(String userId) {
		StringBuilder streamName = new StringBuilder(userId).append(DELIMITER).append(System.currentTimeMillis());
		return streamName.toString();
	}

	public String getUserIdByTrtcUser(String account) {
		return trtcUserIdProvider.getUserIdByTrtcUser(trtcProperties.getSdkappid(), account);
	}

	public String getTrtcUserByUserId(String userId) {
		return trtcUserIdProvider.getTrtcUserByUserId(trtcProperties.getSdkappid(), userId);
	}

}
