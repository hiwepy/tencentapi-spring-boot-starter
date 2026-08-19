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

/**
 * <p>Friend Update Item.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FriendUpdateItem {

	/**
	 * 好友的 UserID
	 */
	@JsonProperty("To_Account")
	private String account;
	/**
	 * 需要更新的关系链数据对象数组
	 */
	@JsonProperty("SnsItem")
	private List<SnsItem> customItem;
		
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public class SnsItem {
	
		/**
		 * 需要更新的关系链字段的字段名，目前只支持好友备注、好友分组、关系链自定义字段的更新操作，关系链字段的详细信息可参见 好友表
		 */
		@JsonProperty("Tag")
		private String tag;
		/**
		 * 需要更新的关系链字段的值，关系链字段的值类型信息可参见 好友表
		 */
		@JsonProperty("Value")
		private Object value;
	}

}
