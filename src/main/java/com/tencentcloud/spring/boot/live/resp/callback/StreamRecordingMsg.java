package com.tencentcloud.spring.boot.live.resp.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2、录制事件通知
 * https://cloud.tencent.com/document/product/267/47026
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamRecordingMsg {
	
	/**
	 * 事件通知安全签名 sign = MD5（key + t）。
	 * 说明：腾讯云把加密 key 和 t 进行字符串拼接后通过 MD5 计算得出 sign 值，并将其放在通知消息里，您的后台服务器在收到通知消息后可以根据同样的算法确认 sign 是否正确，进而确认消息是否确实来自腾讯云后台。
	 */
	@JsonProperty(value = "sign")
	private String sign;
	
	/**
	 * 过期时间，事件通知签名过期 UNIX 时间戳。
	 * 来自腾讯云的消息通知默认过期时间是10分钟，如果一条消息通知中的 t 值所指定的时间已经过期，则可以判定这条通知无效，进而可以防止网络重放攻击。
	 * t 的格式为十进制 UNIX 时间戳，即从1970年01月01日（UTC/GMT 的午夜）开始所经过的秒数。
	 */
	@JsonProperty(value = "t")
	private Long t;
	
	/**
	 * 事件类型编码
	 */
	@JsonProperty(value = "event_type")
	private String event_type;

	/**
	 * 用户 APPID
	 */
	@JsonProperty(value = "appid")
	private String appid;
	
	/**
	 * 同直播流名称
	 */
	@JsonProperty(value = "stream_id")
	private String stream_id;
	
	/**
	 * 点播 file ID，在 云点播平台 可以唯一定位一个点播视频文件
	 */
	@JsonProperty(value = "file_id")
	private String file_id;

	/**
	 * flv，hls，mp4，aac
	 */
	@JsonProperty(value = "file_format")
	private String file_format;

	/**
	 * 录制文件大小，单位字节
	 */
	@JsonProperty(value = "file_size")
	private Long file_size;
	
	/**
	 * 录制文件起始时间戳
	 */
	@JsonProperty(value = "start_time")
	private Long start_time;
	
	/**
	 * 录制文件结束时间戳
	 */
	@JsonProperty(value = "end_time")
	private Long end_time;
	
	/**
	 * 录制文件时长，单位秒
	 */
	@JsonProperty(value = "duration")
	private Long duration;
	
	/**
	 * 用户推流 URL 所带参数
	 */
	@JsonProperty(value = "stream_param")
	private String stream_param;
	
	/**
	 * 录制文件下载 URL
	 */
	@JsonProperty(value = "video_url")
	private String video_url;
	    
}
