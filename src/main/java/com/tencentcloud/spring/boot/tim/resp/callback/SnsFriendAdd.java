package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 添加好友之后回调
 * https://cloud.tencent.com/document/product/269/1657
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class SnsFriendAdd {
	
	/**
	 * 回调命令 - Sns.CallbackFriendAdd
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 * 成功添加的好友对
	 */
	@JsonProperty(value = "PairList")
	private List<SnsFriendAddPair> pairList;

	/**
	 * 触发回调的命令字：
	 *加好友请求，合理的取值如下：friend_add、FriendAdd
	 *加好友回应，合理的取值如下：friend_response、FriendResponse
	 */
	@JsonProperty(value = "ClientCmd")
	private String clientCmd;
	/**
	 * 如果当前请求是后台触发的加好友请求，则该字段被赋值为管理员帐号；否则为空
	 */
	@JsonProperty(value = "Admin_Account")
	private String account;
	/**
	 * 管理员强制加好友标记：1 表示强制加好友；0 表示常规加好友方式
	 */
	@JsonProperty(value = "ForceFlag")
	private int forceFlag;
	    
}
