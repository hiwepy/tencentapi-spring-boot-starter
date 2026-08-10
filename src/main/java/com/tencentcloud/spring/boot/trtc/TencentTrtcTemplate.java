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

/**
 * Facade around the Tencent Real-Time Communication (TRTC) SDK
 * {@link TrtcClient} that exposes room management (kick user / dismiss room)
 * and cloud mix-transcoding operations for both numeric and string room ids.
 * <p>
 * Application user ids are translated to TRTC accounts (and back) through the
 * configured {@link TrtcUserIdProvider}.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Slf4j
public class TencentTrtcTemplate {
	private static final String DELIMITER = "_";

	private final TrtcUserIdProvider trtcUserIdProvider;
	private final TrtcClient trtcClient;
	private final TencentTrtcProperties trtcProperties;

	/**
	 * Wraps the given SDK client, configuration and user-id provider.
	 *
	 * @param trtcClient         the Tencent TRTC SDK client
	 * @param trtcProperties     the bound TRTC configuration
	 * @param trtcUserIdProvider the user-id/account translator
	 */
	public TencentTrtcTemplate(TrtcClient trtcClient, TencentTrtcProperties trtcProperties,
			TrtcUserIdProvider trtcUserIdProvider) {
		this.trtcClient = trtcClient;
		this.trtcProperties = trtcProperties;
		this.trtcUserIdProvider = trtcUserIdProvider;
	}

	/**
	 * Removes users from a numeric room (e.g. host/owner/admin kicking a user).
	 * @see <a href="https://cloud.tencent.com/document/api/647/40496">API reference</a>
	 *
	 * @param roomId  the numeric room id
	 * @param userIds the application user ids to remove
	 * @return the raw SDK response as JSON
	 * @throws TencentCloudSDKException if the SDK call fails
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
	 * Removes users from a string room (e.g. host/owner/admin kicking a user).
	 * @see <a href="https://cloud.tencent.com/document/api/647/50426">API reference</a>
	 *
	 * @param roomId  the string room id
	 * @param userIds the application user ids to remove
	 * @return the raw SDK response as JSON
	 * @throws TencentCloudSDKException if the SDK call fails
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
	 * Dismisses a numeric room, removing all users from it.
	 * @see <a href="https://cloud.tencent.com/document/api/647/50089">API reference</a>
	 *
	 * @param roomId the numeric room id
	 * @return the raw SDK response as JSON
	 * @throws TencentCloudSDKException if the SDK call fails
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
	 * Dismisses a string room, removing all users from it.
	 * @see <a href="https://cloud.tencent.com/document/api/647/37088">API reference</a>
	 *
	 * @param roomId the string room id
	 * @return the raw SDK response as JSON
	 * @throws TencentCloudSDKException if the SDK call fails
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
	 * Builds the mix-transcode {@link OutputParams} for the given stream.
	 *
	 * @param streamId        user-defined live stream id; must differ from any
	 *                       旁路 (relay-to-CDN) stream id
	 * @param pureAudioStream {@code 0} for audio+video (default), {@code 1} for pure audio
	 * @param recordId        custom recording file-name prefix (recording must be
	 *                        enabled in the TRTC console; see
	 *                        <a href="https://cloud.tencent.com/document/product/647/50768">docs</a>)
	 * @param recordAudioOnly {@code 0} is meaningless; {@code 1} forces the
	 *                        recording format to mp3. Prefer configuring a
	 *                        pure-audio recording template in the console.
	 * @return the populated output parameters
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
	 * Starts cloud mix-transcoding for a numeric room with the given layout.
	 * @see <a href="https://cloud.tencent.com/document/api/647/44270">API reference</a>
	 *
	 * @param roomId           the numeric room id
	 * @param outputParams     mix output control params
	 *                       (<a href="https://cloud.tencent.com/document/api/647/44055#OutputParams">OutputParams</a>)
	 * @param encodeParams     mix output encoding params
	 *                       (<a href="https://cloud.tencent.com/document/api/647/44055#EncodeParams">EncodeParams</a>)
	 * @param layoutParams     mix output layout params
	 *                       (<a href="https://cloud.tencent.com/document/api/647/44055#LayoutParams">LayoutParams</a>)
	 * @param publishCdnParams third-party CDN relay params
	 *                       (<a href="https://cloud.tencent.com/document/api/647/44055#PublishCdnParams">PublishCdnParams</a>)
	 * @return the mix-stream address / SDK response as JSON
	 * @throws TencentCloudSDKException if the SDK call fails
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
	 * Starts cloud mix-transcoding for a string room with the given layout.
	 * @see <a href="https://cloud.tencent.com/document/api/647/50236">API reference</a>
	 *
	 * @param roomId           the string room id
	 * @param outputParams     mix output control params
	 *                       (<a href="https://cloud.tencent.com/document/api/647/44055#OutputParams">OutputParams</a>)
	 * @param encodeParams     mix output encoding params
	 *                       (<a href="https://cloud.tencent.com/document/api/647/44055#EncodeParams">EncodeParams</a>)
	 * @param layoutParams     mix output layout params
	 *                       (<a href="https://cloud.tencent.com/document/api/647/44055#LayoutParams">LayoutParams</a>)
	 * @param publishCdnParams third-party CDN relay params
	 *                       (<a href="https://cloud.tencent.com/document/api/647/44055#PublishCdnParams">PublishCdnParams</a>)
	 * @return the mix-stream address / SDK response as JSON
	 * @throws TencentCloudSDKException if the SDK call fails
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
	 * Stops cloud mix-transcoding for a numeric room, retrying on failure up to
	 * the configured retry limit.
	 * @see <a href="https://cloud.tencent.com/document/api/647/44269">API reference</a>
	 *
	 * @param roomId     the numeric room id
	 * @param retryTimes initial retry counter used by the do/while loop
	 * @return {@code true} if mix-transcoding was stopped successfully
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
	 * Stops cloud mix-transcoding for a numeric room without retrying.
	 * @see <a href="https://cloud.tencent.com/document/api/647/44269">API reference</a>
	 *
	 * @param roomId the numeric room id
	 * @return {@code true} if mix-transcoding was stopped successfully
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
	 * Stops cloud mix-transcoding for a string room, retrying on failure up to
	 * the configured retry limit.
	 * @see <a href="https://cloud.tencent.com/document/api/647/50235">API reference</a>
	 *
	 * @param roomId     the string room id
	 * @param retryTimes initial retry counter used by the do/while loop
	 * @return {@code true} if mix-transcoding was stopped successfully
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
	 * Stops cloud mix-transcoding for a string room without retrying.
	 * @see <a href="https://cloud.tencent.com/document/api/647/50235">API reference</a>
	 *
	 * @param roomId the string room id
	 * @return {@code true} if mix-transcoding was stopped successfully
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
	 * Reverses {@link #getStreamNameByUserId(String)} to recover the user id
	 * embedded in a stream name.
	 *
	 * @param streamId the stream id to parse
	 * @return the embedded user id
	 * @throws IllegalArgumentException if the stream name is not in {@code userId_timestamp} form
	 */
	public String getUserIdByStreamName(String streamId) {
		String[] split = streamId.split(DELIMITER);
		if (split.length != 2) {
			throw new IllegalArgumentException("反向解析流名称获取userId异常！");
		}
		return split[0];
	}

	/**
	 * Composes a stream name from a user id and the current timestamp
	 * ({@code userId_timestamp}).
	 *
	 * @param userId the user id
	 * @return the generated stream name
	 */
	public String getStreamNameByUserId(String userId) {
		StringBuilder streamName = new StringBuilder(userId).append(DELIMITER).append(System.currentTimeMillis());
		return streamName.toString();
	}

	/**
	 * Resolves the application user id for a given TRTC account, delegating to
	 * the {@link TrtcUserIdProvider}.
	 *
	 * @param account the TRTC account identifier
	 * @return the application user id
	 */
	public String getUserIdByTrtcUser(String account) {
		return trtcUserIdProvider.getUserIdByTrtcUser(trtcProperties.getSdkappid(), account);
	}

	/**
	 * Resolves the TRTC account for a given application user id, delegating to
	 * the {@link TrtcUserIdProvider}.
	 *
	 * @param userId the application user id
	 * @return the TRTC account identifier
	 */
	public String getTrtcUserByUserId(String userId) {
		return trtcUserIdProvider.getTrtcUserByUserId(trtcProperties.getSdkappid(), userId);
	}

	/** @return the underlying Tencent TRTC SDK client. */
	public TrtcClient getTrtcClient() {
		return trtcClient;
	}

	/** @return the bound TRTC configuration. */
	public TencentTrtcProperties getTrtcProperties() {
		return trtcProperties;
	}

	/** @return the configured user-id/account translator. */
	public TrtcUserIdProvider getTrtcUserIdProvider() {
		return trtcUserIdProvider;
	}

}
