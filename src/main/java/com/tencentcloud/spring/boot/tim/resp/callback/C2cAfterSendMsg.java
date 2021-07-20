package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *发单聊消息之后回调
 * https://cloud.tencent.com/document/product/269/2716
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class C2cAfterSendMsg {
	
	/**
	 * 回调命令 - C2C.CallbackAfterSendMsg
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	
	/**
	 * 发送者
	 */
	@JsonProperty(value = "From_Account")
	private String from;
	
	/**
	 * 接收者
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
	 * 在线消息，为1，否则为0；
	 */
	@JsonProperty(value = "OnlineOnlyFlag")
	private Integer onlineFlag;
	
	/**
	 * 该条消息的下发结果，0表示下发成功，非0表示下发失败
	 */
	@JsonProperty(value = "SendMsgResult")
	private String msgResult;
	
	/**
	 * 该条消息下发失败的错误信息，若消息发送成功，则为"send msg succeed"
	 */
	@JsonProperty(value = "ErrorInfo")
	private String ErrorInfo;
    
	/**
	 * 未读消息数
	 */
	@JsonProperty(value = "UnreadMsgNum")
	private Integer unreadMsgNum;
	
	/**
	 * 消息体，参见 TIMMessage 消息对象
	 */
	@JsonProperty(value = "MsgBody")
	private List<C2cSendMsgBody> msgBody;
	
	/**
	 * 消息自定义数据（云端保存，会发送到对端，程序卸载重装后还能拉取到）
	 */
	@JsonProperty(value = "CloudCustomData")
	private String cloudCustomData;
	    
}
