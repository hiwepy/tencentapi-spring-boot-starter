package com.tencentcloud.spring.boot.tim.resp.sns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Group Delete Response.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = false)
public class GroupDeleteResponse extends TimActionResponse {
	
	/**
	 * 返回最新的分组 Sequence
	 */
	@JsonProperty("CurrentSequence")
	private Integer currentSequence;

}
