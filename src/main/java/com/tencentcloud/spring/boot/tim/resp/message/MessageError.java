/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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
package com.tencentcloud.spring.boot.tim.resp.message;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.req.message.MsgBody;

import lombok.Data;

/**
 * 发送消息失败记录
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class MessageError {
	
	/**
	 * 消息发送失败的目标帐号
	 */
	@JsonProperty("To_Account")
	private String toAccount;

	/**
	 * 消息发送失败的错误码，若目标帐号的错误码为70107表示该帐号不存在
	 */
	@JsonProperty("ErrorCode")
	private Integer errorCode;
	
}
