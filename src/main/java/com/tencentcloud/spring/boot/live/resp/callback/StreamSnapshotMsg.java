package com.tencentcloud.spring.boot.live.resp.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 3、截图事件通知
 * https://cloud.tencent.com/document/product/267/47028
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamSnapshotMsg {
	
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
	 * 截图生成 UNIX 时间戳
	 */
	@JsonProperty(value = "create_time")
	private Long create_time;
	
	/**
	 * 截图文件大小，单位为字节
	 */
	@JsonProperty(value = "file_size")
	private Long file_size;
	
	/**
	 * 截图宽，单位为像素
	 */
	@JsonProperty(value = "width")
	private int width;

	/**
	 * 截图高，单位为像素
	 */
	@JsonProperty(value = "height")
	private int height;
	
	/**
	 * 截图文件路径 /path/name.jpg
	 */
	@JsonProperty(value = "pic_url")
	private String pic_url;
	
	/**
	 * 截图下载 URL
	 */
	@JsonProperty(value = "pic_full_url")
	private String pic_full_url;
	    
}
