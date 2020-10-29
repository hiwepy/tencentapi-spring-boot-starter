package com.tencentcloud.spring.boot.tim.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AccountDeleteResultItem {
	
	/**
	 * 请求删除的帐号的 UserID
	 */
	@JsonProperty("UserID")
	private String userId;
	/**
	 * 单个帐号的错误码，0表示成功，非0表示失败
	 */
	@JsonProperty("ResultCode")
	private Integer resultCode;
	/**
	 * 单个帐号删除失败时的错误描述信息
	 */
	@JsonProperty("ResultInfo")
	private String resultInfo;
	
}
