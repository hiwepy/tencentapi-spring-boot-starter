package com.tencentcloud.spring.boot.tim.req.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 推送消息实体
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlacklistMessage {

	/**
	 * 消息发送方帐号。（用于指定发送消息方帐号）
	 */
	@JsonProperty("From_Account")
	private String fromAccount;

	/**
	 * 拉取的起始位置
	 */
	@JsonProperty("To_Account")
	private List<String> toAccount;

	/**
	 * 社团编号
	 */
	@JsonProperty("StartIndex")
	private Integer startIndex;

	/**
	 * 每页最多拉取的黑名单数
	 */
	@JsonProperty("MaxLimited")
	private Integer maxLimited;

	/**
	 * 上一次拉黑名单时后台返回给客户端的 Seq，初次拉取时为0
	 */
	@JsonProperty("LastSequence")
	private Integer lastSequence;

	/**
	 * 校验模式 校验模式 CheckType 描述 单向校验黑名单关系 BlackCheckResult_Type_Single 只会检查
	 * From_Account 的黑名单中是否有 To_Account，不会检查 To_Account 的黑名单中是否有 From_Account
	 * 双向校验黑名单关系 BlackCheckResult_Type_Both 既会检查 From_Account 的黑名单中是否有
	 * To_Account，也会检查 To_Account 的黑名单中是否有 From_Account
	 */
	@JsonProperty("CheckType")
	private String checkType;
}
