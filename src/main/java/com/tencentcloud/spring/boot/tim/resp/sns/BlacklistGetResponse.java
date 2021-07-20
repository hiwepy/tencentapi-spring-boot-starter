package com.tencentcloud.spring.boot.tim.resp.sns;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlacklistGetResponse extends TimActionResponse {
	
	/**
	 * 黑名单对象数组，每一个黑名单对象都包括了 To_Account 和 AddBlackTimeStamp
	 */
	@JsonProperty("BlackListItem")
	private List<BlackListItem> blackListItem = new ArrayList<>();
	/**
	 * 黑名单最新的 Seq
	 */
	@JsonProperty("CurruentSequence")
	private Integer curruentSequence;
	/**
	 * 下页拉取的起始位置，0表示已拉完
	 */
	@JsonProperty("StartIndex")
	private Integer startIndex;

}
