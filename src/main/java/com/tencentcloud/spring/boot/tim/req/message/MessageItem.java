/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tencentcloud.spring.boot.tim.req.message;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 响应消息记录
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MessageItem {
	
	/**
	 * 消息发送方帐号。（用于指定发送消息方帐号）
	 */
	@JsonProperty("From_Account")
	private String fromAccount;

	/**
	 * 消息接收方帐号。
	 */
	@JsonProperty("To_Account")
	private String toAccount;

	/**
	 * 消息随机数,由随机函数产生。（用作消息去重）
	 */
	@JsonProperty("MsgSeq")
	private Number msgSeq;
	
	/**
	 * 消息随机数,由随机函数产生。（用作消息去重）
	 */
	@JsonProperty("MsgRandom")
	private Number msgRandom;

	/**
	 * 消息时间戳，unix 时间戳。
	 */
	@JsonProperty("MsgTimeStamp")
	private Number msgTimeStamp;

	/**
	 * 该条消息的属性，0表示正常消息，8表示被撤回的消息
	 */
	@JsonProperty("MsgFlagBits")
	private Integer msgFlagBits;
	
	/**
	 * 标识该条消息
	 */
	@JsonProperty("MsgKey")
	private String msgKey;
	
	/**
	 * 消息信息
	 */
	@JsonProperty("MsgBody")
	private List<MsgBody> msgBody;
	
}
