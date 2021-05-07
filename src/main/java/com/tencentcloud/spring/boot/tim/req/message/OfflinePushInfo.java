package com.tencentcloud.spring.boot.tim.req.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 离线推送信息配置
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OfflinePushInfo {

	/**
	 * 0表示推送，1表示不离线推送。
	 */
	@JsonProperty("PushFlag")
	private Integer pushFlag;

	/**
	 * 离线推送标题。该字段为ios和android共用
	 */
	@JsonProperty("Title")
	private String title;

	/**
	 * 离线推送内容 。
	 */
	@JsonProperty("Desc")
	private String desc;

	/**
	 * 离线推送透传内容。
	 */
	@JsonProperty("Ext")
	private String ext;

	@JsonProperty("AndroidInfo")
	private AndroidInfo androidInfo;
	
	@JsonProperty("ApnsInfo")
	private ApnsInfo apnsInfo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	class AndroidInfo {
		
		/**
		 * 离线推送声音文件路径。
		 */
		@JsonProperty("Sound")
		private String sound;
 
	}
	

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	class ApnsInfo {
		/**
		 * 这个字段缺省或者为 0 表示需要计数， 为 1 表示本条消息不需要计数，即右上角图标数字不增加
		 */
		@JsonProperty("badgeMode")
		private Integer badgeMode;
		/**
		 * 离线推送声音文件路径。
		 */
		@JsonProperty("sound")
		private String sound;

		/**
		 * 该字段用于标识apns推送的标题，若填写则会覆盖最上层Title
		 */
		@JsonProperty("title")
		private String title;

		/**
		 * 该字段用于标识apns推送的子标题
		 */
		@JsonProperty("subTitle")
		private String subTitle;

		/**
		 * 该字段用于标识apns携带的图片地址，当客户端拿到该字段时， 可以通过下载图片资源的方式将图片展示在弹窗上
		 */
		@JsonProperty("image")
		private String image;
 
	}
 
}
