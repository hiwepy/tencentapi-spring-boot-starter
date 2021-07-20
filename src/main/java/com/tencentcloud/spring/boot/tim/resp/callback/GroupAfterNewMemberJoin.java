package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 新成员入群之后回调
 * https://cloud.tencent.com/document/product/269/1667
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupAfterNewMemberJoin {
	
	/**
	 * 回调命令 - Group.CallbackAfterNewMemberJoin
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
	 *入群方式：Apply（申请入群）；Invited（邀请入群）
	 */
	@JsonProperty(value = "JoinType")
	private String joinType;
	/**
	 *请求的操作者 UserID
	 */
	@JsonProperty(value = "Operator_Account")
	private String account;
	
	/**
	 * 新入群成员列表
	 */
	@JsonProperty(value = "NewMemberList")
	private List<GroupAfterNewMemberJoinMember> memberList;
	
}
