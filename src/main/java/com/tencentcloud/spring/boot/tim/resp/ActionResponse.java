package com.tencentcloud.spring.boot.tim.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * IM响应结果
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ActionResponse {

	/**
	 * 请求处理的结果，OK 表示处理成功，FAIL 表示失败。
	 */
	@JsonProperty("ActionStatus")
	private String actionStatus;

	/**
	 * 错误信息。
	 */
	@JsonProperty("ErrorInfo")
	private String errorInfo;

	/**
	 * 错误码。
	 */
	@JsonProperty("ErrorCode")
	private int errorCode;

	public boolean isSuccess() {
		return "OK".equals(actionStatus);
	}
}
