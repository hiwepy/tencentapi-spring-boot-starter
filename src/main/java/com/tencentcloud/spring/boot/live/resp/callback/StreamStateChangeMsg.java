package com.tencentcloud.spring.boot.live.resp.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 1、推断流事件通知
 * https://cloud.tencent.com/document/product/267/47025
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamStateChangeMsg {
	
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
	 * 事件消息产生的 UNIX 时间戳
	 */
	@JsonProperty(value = "event_time")
	private Long event_time;
	
	/**
	 * 用户 APPID
	 */
	@JsonProperty(value = "appid")
	private String appid;
	
	/**
	 * 推流域名
	 */
	@JsonProperty(value = "app")
	private String app;
	
	/**
	 * 推流路径
	 */
	@JsonProperty(value = "appname")
	private String appname;
	
	/**
	 * 直播流名称
	 */
	@JsonProperty(value = "stream_id")
	private String stream_id;
	
	/**
	 * 同直播流名称
	 */
	@JsonProperty(value = "channel_id")
	private String channel_id;
	
	/**
	 * 消息序列号，标识一次推流活动，一次推流活动会产生相同序列号的推流和断流消息
	 */
	@JsonProperty(value = "sequence")
	private String sequence;
	
	/**
	 * 直播接入点的 IP
	 */
	@JsonProperty(value = "node")
	private String node;

	/**
	 * 用户推流 IP
	 */
	@JsonProperty(value = "user_ip")
	private String user_ip;
	
	/**
	 * 用户推流 URL 所带参数
	 */
	@JsonProperty(value = "stream_param")
	private String stream_param;
	
	/**
	 * 断流事件通知推流时长，单位毫秒
	 */
	@JsonProperty(value = "push_duration")
	private String push_duration;

	/**
	 * 推断流错误码
	 */
	@JsonProperty(value = "errcode")
	private String errcode;

	/**
	 * 推断流错误描述
	 */
	@JsonProperty(value = "errmsg")
	private String errmsg;

	/**
	 * 判断是否为国内外推流。1-6为国内，7-200为国外
	 */
	@JsonProperty(value = "set_id")
	private String set_id;
	    
}
