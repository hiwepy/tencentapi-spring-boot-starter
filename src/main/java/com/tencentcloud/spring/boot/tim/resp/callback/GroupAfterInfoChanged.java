package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 群组资料修改之后回调
 * https://cloud.tencent.com/document/product/269/2930
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class GroupAfterInfoChanged {
	
	/**
	 * 回调命令 - Group.CallbackAfterGroupInfoChanged
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 *群资料被修改的群组 ID
	 */
	@JsonProperty(value = "GroupId")
	private String groupId;
	
	/**
	 *群资料被修改的群的 群组类型介绍，例如 Public
	 */
	@JsonProperty(value = "Type")
	private String type;
	
	/**
	 *操作者 UserID
	 */
	@JsonProperty(value = "Operator_Account")
	private String operator;
	
	/**
	 *修改后的群名称
	 */
	@JsonProperty(value = "Name")
	private String groupName;
	
	/**
	 *修改后的群简介
	 */
	@JsonProperty(value = "Introduction")
	private String introduction;
	
	/**
	 *修改后的群公告
	 */
	@JsonProperty(value = "Notification")
	private String notification;
	
	/**
	 *修改后的群头像 URL
	 */
	@JsonProperty(value = "FaceUrl")
	private String faceUrl;
	
}
