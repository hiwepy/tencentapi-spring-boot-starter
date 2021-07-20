package com.tencentcloud.spring.boot.tim.resp.profile;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfilePortraitGetResponse extends TimActionResponse  {

	/**
	 * 返回的用户资料结构化信息
	 */
	@JsonProperty("UserProfileItem")
	private List<UserProfileItem> profiles;
 
	/**
	 * 返回处理失败的用户列表，仅当存在失败用户时才返回该字段
	 */
	@JsonProperty("Fail_Account")
	private List<String> failAccounts;

	/**
	 * 返回处理失败的用户列表，仅当存在失败用户时才返回该字段
	 */
	@JsonProperty("CurrentStandardSequence")
	private Integer currentStandardSequence;
	
}
