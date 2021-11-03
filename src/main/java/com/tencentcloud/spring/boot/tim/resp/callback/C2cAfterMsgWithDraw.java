package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *单聊消息撤回后回调
 * https://cloud.tencent.com/document/product/269/61697
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class C2cAfterMsgWithDraw {
	
	/**
	 * 回调命令 - C2C.CallbackAfterMsgReport
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	
	/**
	 * 消息发送者 UserID
	 */
	@JsonProperty(value = "From_Account")
	private String from;
	
	/**
	 * 消息接收者 UserID
	 */
	@JsonProperty(value = "To_Account")
	private String to;
	
	/**
	 * 该条消息的唯一标识
	 */
	@JsonProperty(value = "MsgKey")
	private String msgKey;
	
	/**
	 * To_Account 未读的单聊消息总数量（包含所有的单聊会话）
	 */
	@JsonProperty(value = "UnreadMsgNum")
	private Integer unreadMsgNum;
	    
}
