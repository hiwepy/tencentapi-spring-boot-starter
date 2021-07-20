package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 拉人入群之前回调
 * https://cloud.tencent.com/document/product/269/1666
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupBeforeInviteJoin {
	
	/**
	 * 回调命令 - Group.CallbackBeforeInviteJoinGroup
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 *要将其他用户拉入的群组 ID
	 */
	@JsonProperty(value = "GroupId")
	private String groupId;
	/**
	 *请求的操作者 UserID
	 */
	@JsonProperty(value = "Operator_Account")
	private String account;
	/**
	 *请求创建的 群组类型介绍，例如 Public
	 */
	@JsonProperty(value = "Type")
	private String type;
	/**
	 * 要拉入群组的 UserID 集合
	 */
	@JsonProperty(value = "DestinationMembers")
	private List<GroupBeforeInviteJoinMember> memberList;
	
}
