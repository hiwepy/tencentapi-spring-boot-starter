package com.tencentcloud.spring.boot.tim.resp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfilePortraitGetProfileItem {

	/**
	 * 返回的用户的 UserID
	 */
	@JsonProperty("To_Account")
	private String account;

	/**
	 * 返回的用户的资料对象数组，数组中每一个对象都包含了 Tag 和 Value
	 */
	@JsonProperty("ProfileItem")
	private List<ProfileItem> profileItems;

	/**
	 * To_Account 的处理结果，0表示成功，非0表示失败
	 */
	@JsonProperty("ResultCode")
	private String resultCode;

	/**
	 * To_Account 的错误描述信息，成功时该字段为空
	 */
	@JsonProperty("ResultInfo")
	private String resultInfo;
 
}
