package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 状态变更回调
 * https://cloud.tencent.com/document/product/269/2570
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateChange {
	
	/**
	 * 回调命令 - 状态变更 State.StateChange
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 * 用户上下线的信息
	 */
	@JsonProperty(value = "Info")
	private StateChangeInfo info;

}
