package com.tencentcloud.spring.boot.tim.resp.sns;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class InfoItem {
	
	/**
	 * 请求校验的用户的 UserID
	 */
	@JsonProperty("To_Account")
	private String account;
	/**
	 * 校验成功时 To_Account 与 From_Account 之间的好友关系，详情可参见 校验好友
	 */
	@JsonProperty("Relation")
	private String relation;
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
	
}
