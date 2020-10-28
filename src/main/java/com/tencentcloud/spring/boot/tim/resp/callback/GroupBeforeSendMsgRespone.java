package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 回调应答对象
 */
@Data
public class GroupBeforeSendMsgRespone {
	
	/**
	 * 请求处理的结果，OK 表示处理成功，FAIL 表示失败
	 */
	@JsonProperty(value = "ActionStatus")
	private String ActionStatus = "OK";
	/**
	 * 错误码，0为允许发言；1为拒绝发言；2为静默丢弃
	 */
	@JsonProperty(value = "ErrorCode")
	private Integer ErrorCode = 0;
	/**
	 * 错误信息
	 */
	@JsonProperty(value = "ErrorInfo")
	private String ErrorInfo = "";
	/**
	 * 经过App修改之后的消息体，云通讯后台将把修改后的消息发送到群组中
	 */
	@JsonProperty(value = "MsgBody")
	private List<GroupSendMsgBody> MsgBody;
	
}
