package com.tencentcloud.spring.boot.tim.resp.message;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = false)
public class MessageSendResponse extends TimActionResponse {
	 
	/**
	 * 消息时间戳，UNIX 时间戳
	 */
	@JsonProperty("MsgTime")
	private Integer msgTime;
	/**
	 * 消息唯一标识，用于撤回。长度不超过50个字符
	 */
	@JsonProperty("MsgKey")
	private String msgKey;
	/**
	 * 发送消息失败列表
	 */
	@JsonProperty("ErrorList")
	private List<MessageError> errorList;

}
