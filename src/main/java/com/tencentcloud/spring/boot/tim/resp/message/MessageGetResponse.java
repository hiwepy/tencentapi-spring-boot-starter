package com.tencentcloud.spring.boot.tim.resp.message;

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
@JsonIgnoreProperties(ignoreUnknown = false)
public class MessageGetResponse extends TimActionResponse {
	
	/**
	 * 是否全部拉取，0表示未全部拉取，需要续拉，1表示已全部拉取
	 */
	@JsonProperty("Complete")
	private Integer complete;
	/**
	 * 本次拉取到的消息条数
	 */
	@JsonProperty("MsgCnt")
	private Integer msgCnt;
	/**
	 * 本次拉取到的消息里的最后一条消息的时间
	 */
	@JsonProperty("LastMsgTime")
	private Integer lastMsgTime;
	/**
	 * 本次拉取到的消息里的最后一条消息的标识
	 */
	@JsonProperty("LastMsgKey")
	private String lastMsgKey;
	/**
	 * 返回的消息列表
	 */
	@JsonProperty("MsgList")
	private List<MessageItem> msgList;

}
