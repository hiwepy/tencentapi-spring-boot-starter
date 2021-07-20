package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 回调应答对象
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class CallbackRespone {
	
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
	
	
}
