package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 群成员离开之后回调
 * https://cloud.tencent.com/document/product/269/1668
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupAfterMemberExit {
	
	/**
	 * 回调命令 - Group.CallbackAfterMemberExit
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 *群组 ID
	 */
	@JsonProperty(value = "GroupId")
	private String groupId;
	/**
	 *请求创建的 群组类型介绍，例如 Public
	 */
	@JsonProperty(value = "Type")
	private String type;
	/**
	 *成员离开方式：Kicked-被踢；Quit-主动退群
	 */
	@JsonProperty(value = "ExitType")
	private String exitType;
	/**
	 *请求的操作者 UserID
	 */
	@JsonProperty(value = "Operator_Account")
	private String account;
	
	/**
	 * 离开群的成员列表
	 */
	@JsonProperty(value = "ExitMemberList")
	private List<GroupAfterMemberExitMember> memberList;
	
}
