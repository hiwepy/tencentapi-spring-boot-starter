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
public class FriendCheckResponse extends TimActionResponse {

	/**
	 * 校验结果对象数组
	 */
	@JsonProperty("InfoItem")
	private List<InfoItem> infoItem;
	/**
	 * 返回处理失败的用户列表，仅当存在失败用户时才返回该字段
	 */
	@JsonProperty("Fail_Account")
	private List<String> failAccounts;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	class InfoItem {
		
		/**
		 * 请求校验的用户的 UserID
		 */
		@JsonProperty("To_Account")
		private String account;
		/**
		 * 校验成功时 To_Account 与 From_Account 之间的好友关系，详情可参见 校验好友
		 */
		@JsonProperty("Relation")
		private String relation;
		/**
		 * To_Account 的处理结果，0表示成功，非0表示失败，非0取值的详细描述请参见 错误码说明
		 */
		@JsonProperty("ResultCode")
		private Integer resultCode;
		/**
		 * To_Account 的错误描述信息，成功时该字段为空
		 */
		@JsonProperty("ResultInfo")
		private String resultInfo;
		
	}

}
