package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 群组解散之后回调
 * https://cloud.tencent.com/document/product/269/1670
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupAfterDestroyed {
	
	/**
	 * 回调命令 - Group.CallbackAfterGroupDestroyed
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 *被解散的群组 ID
	 */
	@JsonProperty(value = "GroupId")
	private String groupId;
	/**
	 *被解散的群组名称
	 */
	@JsonProperty(value = "Name")
	private String groupName;
	
	/**
	 *请求创建的 群组类型介绍，例如 Public
	 */
	@JsonProperty(value = "Type")
	private String type;
	
	/**
	 *群主 UserID
	 */
	@JsonProperty(value = "Owner_Account")
	private String owner;
	
	/**
	 * 被解散的群组中的成员
	 */
	@JsonProperty(value = "MemberList")
	private List<GroupAfterDestroyedMember> memberList;
	
}
