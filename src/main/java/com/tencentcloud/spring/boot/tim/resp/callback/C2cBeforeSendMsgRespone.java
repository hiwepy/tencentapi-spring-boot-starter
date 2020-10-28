package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 回调应答对象
 */
@Data
public class C2cBeforeSendMsgRespone {
	
	/**
	 * 请求处理的结果，OK 表示处理成功，FAIL 表示失败
	 */
	@JsonProperty(value = "ActionStatus")
	private String ActionStatus = "OK";
	/**
	 * 错误码，0为允许发言；1为拒绝发言。若业务希望拒绝发言的同时，将错误码 ErrorCode 和 ErrorInfo 传递至客户端，请将错误码 ErrorCode 设置在 [120001, 130000] 区间内
	 */
	@JsonProperty(value = "ErrorCode")
	private Integer ErrorCode = 0;
	/**
	 * 错误信息
	 */
	@JsonProperty(value = "ErrorInfo")
	private String ErrorInfo = "";
	/**
	 * App 修改之后的消息，如果没有，则默认使用用户发送的消息
	 */
	@JsonProperty(value = "MsgBody")
	private List<C2cSendMsgBody> MsgBody;
	
}
