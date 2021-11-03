package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 添加好友回应之前回调
 * https://cloud.tencent.com/document/product/269/61699
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class SnsPrevFriendResponse {
	
	/**
	 * 回调命令 - Sns.CallbackPrevFriendResponse
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 * 触发本次回调的时间戳，单位为毫秒。
	 */
	@JsonProperty(value = "EventTime")
	private Integer eventTime;
	/**
	 * 请求发起方的 UserID
	 */
	@JsonProperty(value = "Requester_Account")
	private String requester;
	/**
	 * 请求添加好友的用户的 UserID
	 */
	@JsonProperty(value = "From_Account")
	private String account;
	/**
	 * 加好友回应请求的参数
	 */
	@JsonProperty(value = "ResponseFriendItem")
	private List<SnsPrevFriendResponseItem> responses;
	
}
