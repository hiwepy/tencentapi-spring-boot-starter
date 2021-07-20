package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 添加黑名单之后回调
 * https://cloud.tencent.com/document/product/269/1660
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SnsBlackListAdd {
	
	/**
	 * 回调命令 - Sns.CallbackBlackListAdd
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	/**
	 * 成功添加的黑名单关系链对
	 */
	@JsonProperty(value = "PairList")
	private List<SnsBlackListAddPair> pairList;
	    
}
