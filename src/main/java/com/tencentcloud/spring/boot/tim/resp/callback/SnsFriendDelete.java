package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 删除好友之后回调
 * https://cloud.tencent.com/document/product/269/1659
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SnsFriendDelete {
	
	/**
	 * 回调命令 - Sns.CallbackFriendDelete
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 * 成功删除的好友
	 */
	@JsonProperty(value = "PairList")
	private List<SnsFriendDeletePair> pairList;
	    
}
