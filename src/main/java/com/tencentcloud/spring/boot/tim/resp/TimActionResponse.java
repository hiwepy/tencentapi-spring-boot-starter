package com.tencentcloud.spring.boot.tim.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * IM响应结果
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class TimActionResponse {

	/**
	 * 请求处理的结果，OK 表示处理成功，FAIL 表示失败。
	 */
	@JsonProperty("ActionStatus")
	private String actionStatus = "FAIL";

	/**
	 * 错误码，0表示成功，非0表示失败
	 */
	@JsonProperty("ErrorCode")
	private int errorCode = 0;
	
	/**
	 * 错误信息。
	 */
	@JsonProperty("ErrorInfo")
	private String errorInfo;

	/**
	 * 详细的客户端展示信息
	 */
	@JsonProperty("ErrorDisplay")
	private String errorDisplay;

	public boolean isSuccess() {
		return "OK".equals(actionStatus);
	}
	 
}
