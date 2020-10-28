package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 群内发言之后回调
 * https://cloud.tencent.com/document/product/269/2661
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupAfterSendMsg {
	
	/**
	 * 回调命令 - Group.CallbackAfterSendMsg
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	

	/**
	 *产生群消息的群组 ID
	 */
	@JsonProperty(value = "GroupId")
	private String groupId;
	
	/**
	 *产生群消息的 群组类型介绍，例如 Public
	 */
	@JsonProperty(value = "Type")
	private String type;
	
	/**
	 * 消息发送者 UserID
	 */
	@JsonProperty(value = "From_Account")
	private String from;
	
	/**
	 * 请求发起者 UserID，可以用来识别是否为管理员请求的
	 */
	@JsonProperty(value = "Operator_Account")
	private String operator;
	
	/**
	 * 息序列号，一条消息的唯一标示；群聊消息使用 MsgSeq 进行排序，MsgSeq 值越大消息越靠后
	 */
	@JsonProperty(value = "MsgSeq")
	private String msgSeq;
	
	/**
	 * 消息发送的时间戳，对应后台 Server 时间
	 */
	@JsonProperty(value = "MsgTime")
	private String msgTime;
    
	/**
	 * 消息体，参见 TIMMessage 消息对象
	 */
	@JsonProperty(value = "MsgBody")
	private List<GroupSendMsgBody> msgBody;
	    
}
