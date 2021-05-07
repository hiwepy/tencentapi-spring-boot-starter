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
package com.tencentcloud.spring.boot.tim.resp.sns;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.ApiResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
public class FriendGetResponse extends ApiResponse {

	/**
	 * 好友对象数组，每一个好友对象都包含一个 To_Account 字段和一个 ValueItem 数组
	 */
	@JsonProperty("UserDataItem")
	private List<UserDataItem> userDataItems;
	/**
	 * 标配好友数据的 Sequence，客户端可以保存该 Sequence，下次请求时通过请求的 StandardSequence 字段返回给后台
	 */
	@JsonProperty("StandardSequence")
	private Integer standardSequence;
	/**
	 * 自定义好友数据的 Sequence，客户端可以保存该 Sequence，下次请求时通过请求的 CustomSequence 字段返回给后台
	 */
	@JsonProperty("CustomSequence")
	private Integer customSequence;
	/**
	 * 好友总数
	 */
	@JsonProperty("FriendNum")
	private Integer friendNum;
	/**
	 * 分页的结束标识，非0值表示已完成全量拉取
	 */
	@JsonProperty("CompleteFlag")
	private Integer completeFlag;
	/**
	 * 分页接口下一页的起始位置
	 */
	@JsonProperty("NextStartIndex")
	private Integer nextStartIndex;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public class UserDataItem {
		
		/**
		 * 好友的 UserID
		 */
		@JsonProperty("To_Account")
		private String account;
		/**
		 * 保存好友数据的数组，数组每一个元素都包含一个 Tag 字段和一个 Value 字段
		 */
		@JsonProperty("ValueItem")
		private List<ValueItem> valueItems;
	
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public class ValueItem {
			
			/**
			 * 好友字段的名称
			 */
			@JsonProperty("Tag")
			private String tag;
			/**
			 * 好友字段的值，详情可参见 关系链字段
			 */
			@JsonProperty("Value")
			private Object value;
			
		}
	
	}

}
