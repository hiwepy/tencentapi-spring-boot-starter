package com.tencentcloud.spring.boot.tim.req.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 推送通知实体
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupNotice {

	/**
	 * 群组ID
	 */
	@JsonProperty("GroupId")
	private String groupId;

	/**
	 * 额外数据
	 */
	@JsonProperty("Data")
	private String data;

}
