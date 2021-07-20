package com.tencentcloud.spring.boot.tim.resp.push;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 用户属性
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAttrs {

	/**
	 * 目标用户帐号
	 */
	@JsonProperty("To_Account")
	private String toAccount;

	/**
	 * 属性集合。每个属性是一个键值对，键为属性名，值为该用户对应的属性值。用户属性值不能超过50字节
	 */
	@JsonProperty("Attrs")
	private Map<String, String> attrs;

}
