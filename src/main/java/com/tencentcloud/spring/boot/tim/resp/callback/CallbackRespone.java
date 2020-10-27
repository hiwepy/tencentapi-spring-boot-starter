package com.tencentcloud.spring.boot.tim.resp.callback;

import lombok.Data;

/**
 * 回调应答对象
 */
@Data
public class CallbackRespone {
	
	/**
	 * 请求处理的结果，OK 表示处理成功，FAIL 表示失败
	 */
	private String ActionStatus = "OK";
	/**
	 * 错误码，0表示 App 后台处理成功，1表示 App 后台处理失败
	 */
	private Integer ErrorCode = 0;
	/**
	 * 错误信息
	 */
	private String ErrorInfo = "";
	
	
}
