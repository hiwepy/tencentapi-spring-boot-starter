package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 创建群组之后回调
 * https://cloud.tencent.com/document/product/269/1663
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupAfterCreate {
	
	/**
	 * 回调命令 - Group.CallbackAfterCreateGroup
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 *操作的群 ID
	 */
	@JsonProperty(value = "GroupId")
	private String groupId;
	/**
	 *操作者
	 */
	@JsonProperty(value = "Operator_Account")
	private String operator;
	/**
	 *群主
	 */
	@JsonProperty(value = "Owner_Account")
	private String owner;
	/**
	 *群组类型
	 */
	@JsonProperty(value = "Type")
	private String type;
	/**
	 *群组名称
	 */
	@JsonProperty(value = "Name")
	private String name;
	/**
	 *该用户已创建的同类的群组个数
	 */
	@JsonProperty(value = "CreatedGroupNum")
	private int createdGroupNum;
	/**
	 * 初始成员列表
	 */
	@JsonProperty(value = "MemberList")
	private List<GroupAfterCreateMember> memberList;
	/**
	 * 用户建群时的自定义字段
	 */
	@JsonProperty(value = "UserDefinedDataList")
	private List<GroupAfterCreateUserDefinedData> UserDefinedDataList;
	
}
