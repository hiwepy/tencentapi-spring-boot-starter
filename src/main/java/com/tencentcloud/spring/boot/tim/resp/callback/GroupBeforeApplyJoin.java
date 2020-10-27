package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 申请入群之前回调
 * https://cloud.tencent.com/document/product/269/1665
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupBeforeApplyJoin {
	
	/**
	 * 回调命令 - Group.CallbackBeforeApplyJoinGroup
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
	 *申请加群者 UserID
	 */
	@JsonProperty(value = "Requestor_Account")
	private String account;
	
}
