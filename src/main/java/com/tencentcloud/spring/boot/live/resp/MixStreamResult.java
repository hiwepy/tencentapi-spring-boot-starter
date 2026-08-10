package com.tencentcloud.spring.boot.live.resp;

import lombok.Builder;
import lombok.Data;

/**
 * Result object holding the session id and push/play URLs for a Tencent Live
 * mix-stream session.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Data
@Builder
public class MixStreamResult {

	/** Mix-stream session id (valid from creation until cancel). */
	private String sessionId;
	/** Output stream name of the mix-stream session. */
	private String streamName;

	/** Push URL, format {@code rtmp://domain/AppName/StreamName?txSecret=...}. */
	private StringBuilder rtmpUrl;
	/** Push URL, format {@code webrtc://domain/AppName/StreamName?txSecret=...}. */
	private StringBuilder webrtcUrl;
	/** Play URL, format {@code http://domain/AppName/StreamName.flv?txSecret=...}. */
	private StringBuilder flvUrl;
	/** Play URL, format {@code http://domain/AppName/StreamName.m3u8}. */
	private StringBuilder hlsUrl;
}
