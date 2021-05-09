package com.tencentcloud.spring.boot.tim.resp.push;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAttrsResponse extends TimActionResponse {

	/**
	 * 用户标签内容列表
	 */
	@JsonProperty("UserAttrs")
	private List<UserAttrs> userAttrs;
}
