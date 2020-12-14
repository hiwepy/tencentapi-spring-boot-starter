package com.tencentcloud.spring.boot.tim.req.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MsgBody {

	/**
	 * 消息元素类别；目前支持的消息对象包括：
	 * TIMTextElem(文本消息)，
	 * TIMFaceElem(表情消息)，
	 * TIMLocationElem(位置消息)，
	 * TIMCustomElem(自定义消息)。
	 */
	@JsonProperty("MsgType")
	private String msgType;

	/**
	 * 消息内容
	 */
	@JsonProperty("MsgContent")
	private MsgContent msgContent;

}
