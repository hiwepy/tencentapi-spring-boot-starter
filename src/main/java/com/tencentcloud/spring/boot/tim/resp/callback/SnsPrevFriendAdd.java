package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 添加好友之前回调
 * https://cloud.tencent.com/document/product/269/61698
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class SnsPrevFriendAdd {
	
	/**
	 * 回调命令 - Sns.CallbackPrevFriendAdd
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
	 * 加好友请求的参数
	 */
	@JsonProperty(value = "FriendItem")
	private List<SnsFriendItem> friends;

	/**
	 * 加好友方式（默认双向加好友方式）：
	 * Add_Type_Single 表示单向加好友
	 * Add_Type_Both 表示双向加好友
	 */
	@JsonProperty(value = "AddType")
	private String addType;
	
	/**
	 * 管理员强制加好友标记：
	 * 1 表示强制加好友
	 * 0 表示常规加好友方式
	 */
	@JsonProperty(value = "ForceAddFlags")
	private Integer forceAddFlags;
	
}
