package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 拉人入群之前回调响应
 * https://cloud.tencent.com/document/product/269/1666
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper=false)
public class GroupBeforeInviteJoinResponse extends CallbackRespone{
	
	/**
	 * 请求处理的结果，OK 表示处理成功，FAIL 表示失败
	 */
	@JsonProperty(value = "ActionStatus")
	private String ActionStatus = "OK";
	/**
	 * 错误码，0表示 App 后台处理成功，1表示 App 后台处理失败
	 */
	@JsonProperty(value = "ErrorCode")
	private Integer ErrorCode = 0;
	/**
	 * 错误信息
	 */
	@JsonProperty(value = "ErrorInfo")
	private String ErrorInfo = "";
	/**
	 * 拒绝加入的用户列表
	 */
	@JsonProperty(value = "RefusedMembers_Account")
	private List<String> RefusedMembers_Account;
	                        		
}
