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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
public class GroupMsgRecallResponse extends TimActionResponse {
	
	/**
	 * 消息撤回请求的详细结果
	 */ 
	@JsonProperty("RecallRetList")
	private List<GroupMsgRecallRet> recallRetList;
	
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public class GroupMsgRecallRet {
	
	    /**
	     * 单个被撤回消息的 seq
	     */
	    @JsonProperty("MsgSeq")
	    private String msgSeq;

	    /**
	     * 单个消息的被撤回结果：0表示成功；其它表示失败，参考下文错误码说明
	     */
	    @JsonProperty("RetCode")
	    private String retCode;
	    
	}
}
