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
 * Facade around the Tencent Cloud Live (LVB) SDK {@link LiveClient} that builds
 * push/play stream URLs, manages common stream mixing sessions and queries live
 * stream state.
 * <p>
 * Push and play URLs are signed using the configured stream key and are valid
 * for one week (see {@link #ONE_WEEK_SECOND}). See the
 * <a href="https://cloud.tencent.com/document/product/267/41299">Live docs</a>
 * and the <a href="https://cloud.tencent.com/document/product/267/32744">mix-stream docs</a>.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Slf4j
public class TencentLiveTemplate {

	private static SecureRandom srandom = new SecureRandom();

	/** Number of seconds in one week; used as the validity window for generated stream URLs. */
	public static final Integer ONE_WEEK_SECOND = 7 * 24 * 60 * 60;
	//private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/** Logical input-stream name used for the mix-stream canvas layer. */
	private static final String CANV_NAME = "canv";

	/** Delimiter used when composing/parsing stream names ({@code userId_timestamp}). */
	private static final String DELIMITER = "_";

	private static final Float DEFAULT_PARAM = 0F;
	private static final Float WIDTH = 368F;
	private static final Float HEIGHT = 640F;

	private final LiveClient liveClient;
	private final TencentLiveProperties liveProperties;

	/**
	 * Wraps the given SDK client and configuration.
	 *
	 * @param liveClient      the Tencent Live SDK client
	 * @param liveProperties  the bound Live configuration
	 */
	public TencentLiveTemplate(LiveClient liveClient, TencentLiveProperties liveProperties) {
		this.liveClient = liveClient;
		this.liveProperties = liveProperties;
	}

	/**
	 * Builds a set of push/play URLs for the stream derived from the given user id.
	 *
	 * @param userId the user id used to derive the stream name
	 * @return the generated stream URLs (RTMP / WebRTC / FLV / HLS)
	 */
	public StreamResult createStream(String userId) {
		return createStreamByStreamName(this.getStreamNameByUserId(userId));
	}

	/**
	 * Builds a set of signed push/play URLs for the given stream name.
	 *
	 * @param streamName the stream name; see
	 *                   <a href="https://cloud.tencent.com/document/product/267/43392">docs</a>
	 * @return the generated stream URLs (RTMP / WebRTC / FLV / HLS)
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
	 * Creates a common mix-stream session with a default side-by-side canvas
	 * layout (canvas + home stream + away stream).
	 * @see <a href="https://cloud.tencent.com/document/product/267/43404">API reference</a>
	 *
	 * @param homeStreamName home (primary) stream name
	 * @param awayStreamName away (secondary) stream name
	 * @param retryTimes     initial retry counter used by the do/while loop
	 * @return the mix-stream result containing the session id and output URLs
	 */
	public MixStreamResult createMixStream(String homeStreamName, String awayStreamName, int retryTimes) {

		// Output stream configuration.
		CommonMixOutputParams outputParams = new CommonMixOutputParams();
		String outputStreamName = System.currentTimeMillis() + DELIMITER + srandom.nextInt(10000);
		outputParams.setOutputStreamName(outputStreamName);
		outputParams.setOutputStreamType(1L);

		CommonMixInputParam[] inputStreams = new CommonMixInputParam[3];

		// Canvas (background) layer.
		CommonMixInputParam inputStream = new CommonMixInputParam();
		inputStream.setInputStreamName(CANV_NAME);
		CommonMixLayoutParams LayoutParams = new CommonMixLayoutParams();
		LayoutParams.setImageLayer(1L);
		LayoutParams.setInputType(3L);
		LayoutParams.setImageWidth(2 * WIDTH);
		LayoutParams.setImageHeight(HEIGHT);
		inputStream.setLayoutParams(LayoutParams);
		inputStreams[0] = inputStream;

		// Home (primary) picture layer.
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

		// Away (secondary) picture layer.
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
	 * Creates a common mix-stream session using the caller-supplied input streams
	 * and output parameters.
	 * @see <a href="https://cloud.tencent.com/document/product/267/43404">API reference</a>
	 *
	 * @param homeStreamName home (primary) stream name, used to derive the session id
	 * @param awayStreamName away (secondary) stream name (reserved for logging)
	 * @param retryTimes     initial retry counter used by the do/while loop
	 * @param inputStreams   the mix-stream input stream list
	 * @param outputParams   the mix-stream output parameters
	 * @return the mix-stream result containing the session id and output URLs
	 */
	public MixStreamResult createMixStream(String homeStreamName, String awayStreamName, int retryTimes,
			CommonMixInputParam[] inputStreams,
			CommonMixOutputParams outputParams) {
		MixStreamResult result = null;
		boolean isSuccess;
		do {
			try {

				CreateCommonMixStreamRequest req = new CreateCommonMixStreamRequest();

				// Mix-stream session id (valid from creation until cancel).
				String mixStreamSessionId = CommonHelper.getMixStreamSessionId(homeStreamName);

				req.setInputStreamList(inputStreams);
				req.setMixStreamSessionId(mixStreamSessionId);
				req.setOutputParams(outputParams);

				CreateCommonMixStreamResponse commonMixStreamResponse = liveClient.CreateCommonMixStream(req);

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
	 * Creates a common mix-stream session with explicit control parameters.
	 * @see <a href="https://cloud.tencent.com/document/product/267/43404">API reference</a>
	 *
	 * @param homeStreamName home (primary) stream name, used to derive the session id
	 * @param awayStreamName away (secondary) stream name (reserved for logging)
	 * @param retryTimes     initial retry counter used by the do/while loop
	 * @param controlParams  special control parameters for the mix stream
	 * @param inputStreams   the mix-stream input stream list
	 * @param outputParams   the mix-stream output parameters
	 * @return the mix-stream result containing the session id and output URLs
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

				// Mix-stream session id (valid from creation until cancel).
				String mixStreamSessionId = CommonHelper.getMixStreamSessionId(homeStreamName);

				req.setControlParams(controlParams);
				req.setInputStreamList(inputStreams);
				req.setMixStreamSessionId(mixStreamSessionId);
				req.setOutputParams(outputParams);

				CreateCommonMixStreamResponse commonMixStreamResponse = liveClient.CreateCommonMixStream(req);

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
	 * Cancels a common mix-stream session, retrying on failure up to the
	 * configured retry limit.
	 * @see <a href="https://cloud.tencent.com/document/product/267/43405">API reference</a>
	 *
	 * @param mixStreamSessionId the mix-stream session id to cancel
	 * @param retryTimes         initial retry counter used by the do/while loop
	 * @return {@code true} if the session was cancelled successfully
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
	 * Cancels a common mix-stream session without retrying.
	 *
	 * @param mixStreamSessionId the mix-stream session id to cancel
	 * @return {@code true} if the session was cancelled successfully, {@code false} otherwise
	 */
	public boolean stopMixStream(String mixStreamSessionId) {
		if (StringUtils.hasText(mixStreamSessionId)) {
			try {
				CancelCommonMixStreamRequest req = new CancelCommonMixStreamRequest();
				req.setMixStreamSessionId(mixStreamSessionId);
				CancelCommonMixStreamResponse commonMixStreamResponse = liveClient.CancelCommonMixStream(req);
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
	 * Queries the state of a live stream.
	 *
	 * @param streamName the stream name to query
	 * @return the stream state: {@code active}, {@code inactive} or {@code forbid}
	 * @throws TencentCloudSDKException if the SDK call fails
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
	 * Reverses {@link #getStreamNameByUserId(String)} to recover the user id
	 * embedded in a stream name.
	 *
	 * @param streamName the stream name to parse
	 * @return the embedded user id
	 * @throws IllegalArgumentException if the stream name is not in {@code userId_timestamp} form
	 */
	public String getUserIdByStreamName(String streamName) {
		String[] split = streamName.split(DELIMITER);
		if (split.length != 2) {
			throw new IllegalArgumentException ("反向解析流名称获取userId异常！");
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

	/** @return the underlying Tencent Live SDK client. */
	public LiveClient getLiveClient() {
		return liveClient;
	}

	/** @return the bound Live configuration. */
	public TencentLiveProperties getLiveProperties() {
		return liveProperties;
	}

}
