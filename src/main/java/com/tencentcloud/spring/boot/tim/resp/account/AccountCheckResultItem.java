package com.tencentcloud.spring.boot.tim.resp.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AccountCheckResultItem {
	
	/**
	 * 请求检查的帐号的 UserID
	 */
	@JsonProperty("UserID")
	private String userId;
	/**
	 * 单个帐号的导入状态：Imported 表示已导入，NotImported 表示未导入
	 */
	@JsonProperty("AccountStatus")
	private String accountStatus;
	/**
	 * 单个帐号的检查结果：0表示成功，非0表示失败
	 */
	@JsonProperty("ResultCode")
	private Integer resultCode;
	/**
	 * 单个帐号检查失败时的错误描述信息
	 */
	@JsonProperty("ResultInfo")
	private String resultInfo;
	
}
