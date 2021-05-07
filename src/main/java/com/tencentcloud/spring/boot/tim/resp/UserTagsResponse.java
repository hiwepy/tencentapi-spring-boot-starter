package com.tencentcloud.spring.boot.tim.resp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.push.UserTags;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTagsResponse extends ApiResponse {

	/**
	 * 用户标签内容列表
	 */
	@JsonProperty("UserTags")
	private List<UserTags> userTags;
}
