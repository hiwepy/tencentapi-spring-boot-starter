package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 删除黑名单之后回调
 * https://cloud.tencent.com/document/product/269/1661
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SnsBlackListDelete {
	
	/**
	 * 回调命令 - Sns.CallbackBlackListDelete
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 * 成功删除的黑名单对
	 */
	@JsonProperty(value = "PairList")
	private List<SnsBlackListDeletePair> pairList;
	    
}
