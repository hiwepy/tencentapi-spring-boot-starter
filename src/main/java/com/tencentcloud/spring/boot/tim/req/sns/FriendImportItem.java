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
package com.tencentcloud.spring.boot.tim.req.sns;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FriendImportItem {

	/**
	 * 好友的 UserID
	 */
	@JsonProperty("To_Account")
	private String account;
	/**
	 * From_Account 对 To_Account 的好友备注
	 */
	@JsonProperty("Remark")
	private String remark;
	/**
	 * rom_Account 对 To_Account 的好友备注时间
	 */
	@JsonProperty("RemarkTime")
	private String remarkTime;
	/**
	 * From_Account 对 To_Account 的分组信息，添加好友时只允许设置一个分组，因此使用 String 类型即可，详情可参见 标配好友字段
	 */
	@JsonProperty("GroupName")
	private String groupName;
	/**
	 * 加好友来源字段，详情可参见 标配好友字段
	 */
	@JsonProperty("AddSource")
	private String source;
	/**
	 * From_Account 和 To_Account 形成好友关系时的附言信息，详情可参见 标配好友字段
	 */
	@JsonProperty("AddWording")
	private String wording;
	/**
	 * From_Account 和 To_Account 形成好友关系的时间
	 */
	@JsonProperty("AddTime")
	private String addTime;
	/**
	 * From_Account 对 To_Account 的自定义好友数据，每一个成员都包含一个 Tag 字段和一个 Value 字段，详情可参见 自定义好友字段
	 */
	@JsonProperty("CustomItem")
	private List<CustomItem> customItem;

}
