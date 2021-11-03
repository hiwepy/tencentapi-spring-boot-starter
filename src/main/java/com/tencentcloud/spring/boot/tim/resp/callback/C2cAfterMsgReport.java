package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *单聊消息已读上报后回调
 * https://cloud.tencent.com/document/product/269/61696
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class C2cAfterMsgReport {
	
	/**
	 * 回调命令 - C2C.CallbackAfterMsgReport
	 */
	@JsonProperty(value = "CallbackCommand")
	private String command;
	
	/**
	 * 已读上报方 UserID
	 */
	@JsonProperty(value = "Report_Account")
	private String report;
	
	/**
	 * 会话对端 UserID
	 */
	@JsonProperty(value = "Peer_Account")
	private String peer;
	
	/**
	 * 已读时间
	 */
	@JsonProperty(value = "LastReadTime")
	private Integer lastReadTime;
	
	/**
	 * Report_Account 未读的单聊消息总数量（包含所有的单聊会话）
	 */
	@JsonProperty(value = "UnreadMsgNum")
	private Integer unreadMsgNum;
	    
}
