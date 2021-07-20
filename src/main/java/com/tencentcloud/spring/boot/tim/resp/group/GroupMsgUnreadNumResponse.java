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
package com.tencentcloud.spring.boot.tim.resp.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMsgUnreadNumResponse extends TimActionResponse {

	/**
	 * 具体的消息导入结果
	 */ 
	@JsonProperty("ImportMsgResult")
	private List<GroupMsgImportResult> result;
	
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public class GroupMsgImportResult {

	    /**
	     * 单条消息导入结果
	     * 0表示单条消息成功
	     * 10004表示单条消息发送时间无效
	     * 80001表示单条消息包含脏字，拒绝存储此消息
	     * 80002表示为消息内容过长，目前支持8000字节的消息，请调整消息长度
	     */
	    @JsonProperty("Result")
	    private Integer result;
	    
	    /**
	     * 消息的时间戳
	     */
	    @JsonProperty("MsgTime")
	    private Integer msgTime;
	}
	
}
