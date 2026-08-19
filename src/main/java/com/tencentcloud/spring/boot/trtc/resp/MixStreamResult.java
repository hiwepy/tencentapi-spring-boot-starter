package com.tencentcloud.spring.boot.trtc.resp;

import lombok.Builder;
import lombok.Data;

/**
 * <p>Mix Stream Result.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Data
@Builder
public class MixStreamResult {

	private String sessionId;
	private String streamName;

	/**
	 * 格式rtmp://domain/AppName/StreamName?txSecret=
	 */
	private String rtmpUrl;
	/**
	 * 格式http://domain/AppName/StreamName.flv?txSecret=
	 */
	private String flvUrl;
	/**
	 * 格式http://domain/AppName/StreamName.m3u8
	 */
	private String hlsUrl;
}
