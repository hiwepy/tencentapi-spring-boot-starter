package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * <p>State Change Kicked Device.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class StateChangeKickedDevice {
	
	/**
	 * 被踢下线的设备的平台类型，可能的取值有"iOS", "Android", "Web", "Windows", "iPad", "Mac", "Linux"。
	 */
	@JsonProperty(value = "Platform")
	private String platform;
	
}
