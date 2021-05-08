package com.tencentcloud.spring.boot.tim.resp.sns;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupDeleteResponse extends TimActionResponse {
	
	/**
	 * 返回最新的分组 Sequence
	 */
	@JsonProperty("CurrentSequence")
	private Integer currentSequence;

}
