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
public class GroupMemberRoleGetResponse extends TimActionResponse {

	/**
	 * 拉取到的成员角色，包括：Owner(群主)，Admin(群管理员)，Member(普通群成员），NotMember(非群成员)
	 */ 
	@JsonProperty("UserIdList")
	private List<GroupMemberRole> userIdList;
	
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public class GroupMemberRole {
	
	    /**
	     * 成员ID
	     */
	    @JsonProperty("Member_Account")
	    private String account;

	    /**
	     * 成员角色：Owner/Admin/Member/NotMember
	     */
	    @JsonProperty("Role")
	    private String role;
	}
	
}
