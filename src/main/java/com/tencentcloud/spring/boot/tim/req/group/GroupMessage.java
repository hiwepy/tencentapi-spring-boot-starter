package com.tencentcloud.spring.boot.tim.req.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.req.message.MsgBody;
import com.tencentcloud.spring.boot.tim.req.message.OfflinePushInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 推送消息实体
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessage {
	
	/**
	 * 社团编号
	 */
	@JsonProperty("GroupId")
	private String groupId;

	/**
	 * 消息来源帐号，选填。如果不填写该字段，则默认消息的发送者为调用该接口时使用的 App 管理员帐号。除此之外，App 亦可通过该字段“伪造”消息的发送者，从而实现一些特殊的功能需求。需要注意的是，如果指定该字段，必须要确保字段中的帐号是存在的
	 */
	@JsonProperty("From_Account")
	private String account;
	
	/**
	 * 随机数字，五分钟数字相同认为是重复消息
	 */
	@JsonProperty("Random")
	private Integer random;

	/**
	 * 消息的优先级，默认优先级 Normal。
	 * 可以指定3种优先级，从高到低依次为 High、Normal 和 Low，区分大小写。
	 */
	@JsonProperty("MsgPriority")
	private String priority;

	/**
	 * 消息信息
	 */
	@JsonProperty("MsgBody")
	private List<MsgBody> msgBody;
	
	/**
	 * 如果消息体中指定 OnlineOnlyFlag，只要值大于0，则消息表示只在线下发，不存离线和漫游（AVChatRoom 和 BChatRoom 不允许使用）。
	 */
	@JsonProperty("OnlineOnlyFlag")
	private Integer onlineOnlyFlag;

	/**
	 * 消息回调禁止开关，只对单条消息有效，ForbidBeforeSendMsgCallback 表示禁止发消息前回调，ForbidAfterSendMsgCallback 表示禁止发消息后回调
	 */
	@JsonProperty("ForbidCallbackControl")
	private List<String> forbidCallbackControl;
	
	/**
	 * 离线信息设置
	 */
	@JsonProperty("OfflinePushInfo")
	private OfflinePushInfo offlinePushInfo;

}
