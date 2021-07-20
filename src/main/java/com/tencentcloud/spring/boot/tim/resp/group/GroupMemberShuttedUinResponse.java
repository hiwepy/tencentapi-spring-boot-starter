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
@JsonIgnoreProperties(ignoreUnknown = false)
public class GroupMemberShuttedUinResponse extends TimActionResponse {

	/**
	 *群 ID，由即时通信 IM 后台分配
	 */
	@JsonProperty("GroupId")
	private String groupId;
	
	/**
	 * 返回结果为禁言用户信息数组，内容包括被禁言的成员 ID，及其被禁言到的时间（使用 UTC 时间，即世界协调时间）
	 */ 
	@JsonProperty("ShuttedUinList")
	private List<GroupMemberShuttedUin> shuttedUinList;
	
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public class GroupMemberShuttedUin {
	
	    /**
	     * 成员ID
	     */
	    @JsonProperty("Member_Account")
	    private String account;

	    /**
	     * 禁言到的时间（使用 UTC 时间，即世界协调时间）
	     */
	    @JsonProperty("ShuttedUntil")
	    private Long shuttedUntil;
	    
	}
}
