package com.tencentcloud.spring.boot.live.resp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MixStreamResult {

	private String sessionId;
	private String streamName;

	/**
	 * 格式rtmp://domain/AppName/StreamName?txSecret=
	 */
	private StringBuilder rtmpUrl;
	/**
	 * 格式webrtc://domain/AppName/StreamName?txSecret=
	 */
	private StringBuilder webrtcUrl;
	/**
	 * 格式http://domain/AppName/StreamName.flv?txSecret=
	 */
	private StringBuilder flvUrl;
	/**
	 * 格式http://domain/AppName/StreamName.m3u8
	 */
	private StringBuilder hlsUrl;
}
