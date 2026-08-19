package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 状态变更回调
 * https://cloud.tencent.com/document/product/269/2570
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class StateChange {
	
	/**
	 * 回调命令 - 状态变更 State.StateChange
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 * 触发本次回调的时间戳，单位为毫秒。
	 */
	@JsonProperty(value = "EventTime")
	private Integer eventTime;
	/**
	 * 用户上下线的信息
	 */
	@JsonProperty(value = "Info")
	private StateChangeInfo info;
	/**
	 * 如果本次状态变更为 Login + Register，而且有其他设备被踢下线，才会有此字段。此字段表示其他被踢下线的设备的信息。
	 */
	@JsonProperty(value = "KickedDevice")
	private List<StateChangeKickedDevice> kickedDevices;

}
