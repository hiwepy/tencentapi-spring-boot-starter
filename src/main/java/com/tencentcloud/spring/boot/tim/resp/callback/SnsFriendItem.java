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
package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class SnsFriendItem {

	/**
	 * 请求添加的用户的 UserID
	 */
	@JsonProperty(value = "To_Account")
	private String account;

	/**
	 * From_Account 对 To_Account 设置的好友备注，详情可参见 标配好友字段
	 */
	@JsonProperty(value = "Remark")
	private String remark;

	/**
	 * From_Account 对 To_Account 设置的好友分组，详情可参见 标配好友字段
	 */
	@JsonProperty(value = "GroupName")
	private String groupName;

	/**
	 * 加好友来源，详情可参见 标配好友字段
	 */
	@JsonProperty(value = "AddSource")
	private String addSource;

	/**
	 * 加好友附言，详情可参见 标配好友字段
	 */
	@JsonProperty(value = "AddWording")
	private String addWording;
	
}
