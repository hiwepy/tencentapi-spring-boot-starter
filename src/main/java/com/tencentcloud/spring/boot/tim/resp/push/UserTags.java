package com.tencentcloud.spring.boot.tim.resp.push;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 用户标签
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTags {

	/**
	 * 目标用户帐号
	 */
	@JsonProperty("To_Account")
	private String toAccount;

	/**
	 * 标签集合
	 */
	@JsonProperty("Tags")
	private List<String> tags;

}
