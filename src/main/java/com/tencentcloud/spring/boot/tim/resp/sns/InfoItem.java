package com.tencentcloud.spring.boot.tim.resp.sns;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class InfoItem {
	
	/**
	 * 好友的 UserID
	 */
	@JsonProperty("To_Account")
	private String account;
	/**
	 * 保存好友数据的数组，数组每一个元素都包含一个 Tag 字段和一个 Value 字段
	 */
	@JsonProperty("SnsProfileItem")
	private List<SnsProfileItem> relation;
	/**
	 * To_Account 的处理结果，0表示成功，非0表示失败，非0取值的详细描述请参见 错误码说明
	 */
	@JsonProperty("ResultCode")
	private Integer resultCode;
	/**
	 * To_Account 的错误描述信息，成功时该字段为空
	 */
	@JsonProperty("ResultInfo")
	private String resultInfo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public class SnsProfileItem {

		/**
		 * 好友字段的名称
		 */
		@JsonProperty("Tag")
		private String tag;
		/**
		 * 好友字段的值，详情可参见 关系链字段
		 */
		@JsonProperty("Value")
		private Object value;
	
	}
	
}
