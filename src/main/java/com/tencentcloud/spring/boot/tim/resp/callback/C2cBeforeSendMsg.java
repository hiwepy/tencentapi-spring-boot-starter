package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *发单聊消息之前回调
 * https://cloud.tencent.com/document/product/269/1632
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class C2cBeforeSendMsg {
	
	/**
	 * 回调命令 - C2C.CallbackBeforeSendMsg
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	
	/**
	 * 回调命令 - C2C.CallbackBeforeSendMsg
	 */
	@JsonProperty(value = "From_Account")
	private String from;
	
	/**
	 * 回调命令 - C2C.CallbackBeforeSendMsg
	 */
	@JsonProperty(value = "To_Account")
	private String to;
	
	/**
	 * 消息序列号
	 */
	@JsonProperty(value = "MsgSeq")
	private String msgSeq;
	
	/**
	 * 消息随机数
	 */
	@JsonProperty(value = "MsgRandom")
	private String msgRandom;
	
	/**
	 * 消息的发送时间戳，单位为秒
	 */
	@JsonProperty(value = "MsgTime")
	private String msgTime;
	
	/**
	 * 消息的唯一标识，可用于 REST API 撤回单聊消息
	 */
	@JsonProperty(value = "MsgKey")
	private String msgKey;
    
	/**
	 * 消息体，参见 TIMMessage 消息对象
	 */
	@JsonProperty(value = "MsgBody")
	private C2cBeforeSendMsgBody msgBody;
	    
}
