package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 群组满员之后回调
 * https://cloud.tencent.com/document/product/269/1669
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class GroupAfterGroupFull {
	
	/**
	 * 回调命令 - Group.CallbackAfterGroupFull
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	
	/**
	 *满员的群组 ID
	 */
	@JsonProperty(value = "GroupId")
	private String groupId;	   
	
}
