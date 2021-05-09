package com.tencentcloud.spring.boot.tim.req.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.req.message.MsgBody;

import lombok.Data;

/**
 * 消息实体
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GroupMemberImport {
	
	/**
	 * 消息来源帐号，选填。如果不填写该字段，则默认消息的发送者为调用该接口时使用的 App 管理员帐号。除此之外，App 亦可通过该字段“伪造”消息的发送者，从而实现一些特殊的功能需求。需要注意的是，如果指定该字段，必须要确保字段中的帐号是存在的
	 */
	@JsonProperty("From_Account")
	private String account;

	/**
	 * 消息发送时间
	 */
	@JsonProperty("SendTime")
	private Integer sendTime;
	
	/**
	 * 随机数字，五分钟数字相同认为是重复消息
	 */
	@JsonProperty("Random")
	private Integer random;
	
	/**
	 * 消息信息
	 */
	@JsonProperty("MsgBody")
	private List<MsgBody> msgBody;

}
