package com.tencentcloud.spring.boot.tim.resp.sns;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = false)
public class BlacklistAddResponse extends TimActionResponse {

	/**
	 * 批量添加黑名单的结果对象数组
	 */
	@JsonProperty("ResultItem")
	private List<ResultItem> resultItem;

	/**
	 * 返回处理失败的用户列表，仅当存在失败用户时才返回该字段
	 */
	@JsonProperty("Fail_Account")
	private List<String> failAccount;

}
