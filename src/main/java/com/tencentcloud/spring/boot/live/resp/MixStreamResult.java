package com.tencentcloud.spring.boot.live.resp;

import lombok.Data;

@Data
public class MixStreamResult {
    private String sessionId;
    private String streamName;
    /**
     * 格式rtmp://domain/AppName/StreamName?txSecret=
     */
    private StringBuilder rtmpUrl = new StringBuilder();
    /**
     * 格式http://domain/AppName/StreamName.flv?txSecret=
     */
    private StringBuilder flvUrl = new StringBuilder();
    /**
     * 格式http://domain/AppName/StreamName.m3u8
     */
    private StringBuilder hlsUrl = new StringBuilder();
}
