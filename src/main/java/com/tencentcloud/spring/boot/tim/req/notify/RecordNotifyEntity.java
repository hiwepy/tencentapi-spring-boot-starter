package com.tencentcloud.spring.boot.tim.req.notify;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * 录制事件通知 暂时不用 请求实体
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordNotifyEntity {

    /**
     * 事件通知信息类型：推流事件为1；断流事件为0；录制事件为100；截图事件为200。
     */
    @JsonProperty("event_type")
    private Integer eventType;

    /**
     * 事件通知签名
     */
    @JsonProperty("sign")
    private String sign;

    /**
     * 事件通知签名过期 UNIX 时间戳
     */
    @JsonProperty("t")
    private Integer t;

    /**
     * 用户 APPID
     */
    @JsonProperty("appid")
    private Integer appid;

    /**
     * 直播流名称
     */
    @JsonProperty("stream_id")
    private String streamId;

    /**
     * 点播 file ID，在点播平台可以唯一定位一个点播视频文件
     */
    @JsonProperty("file_id")
    private String fileId;

    /**
     * flv，hls，mp4，aac
     */
    @JsonProperty("file_format")
    private String fileFormat;

    /**
     * 录制文件起始时间戳
     */
    @JsonProperty("start_time")
    private Long startTime;

    /**
     * 录制文件结束时间戳
     */
    @JsonProperty("end_time")
    private Long endTime;

    /**
     * 录制文件时长，单位秒
     */
    @JsonProperty("duration")
    private Long duration;

    /**
     * 录制文件大小，单位字节
     */
    @JsonProperty("file_size")
    private Long fileSize;

    /**
     * 用户推流 URL 所带参数
     */
    @JsonProperty("stream_param")
    private String streamParam;

    /**
     * 录制文件文件下载 URL
     */
    @JsonProperty("video_url")
    private String videoUrl;
}
