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
package com.tencentcloud.spring.boot.tim.resp.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AccountItem {

	/**
	 * 请求检查的帐号的 UserID
	 */
	@JsonProperty("UserID")
	private String userId;

	/**
	 * 单个帐号的检查结果：0表示成功，非0表示失败
	 */
	@JsonProperty("ResultCode")
	private String resultCode;

	/**
	 * 单个帐号检查失败时的错误描述信息
	 */
	@JsonProperty("ResultInfo")
	private String resultInfo;

	/**
	 * 单个帐号的导入状态：Imported 表示已导入，NotImported 表示未导入
	 */
	@JsonProperty("accountStatus")
	private String accountStatus;
	
	public boolean isImported() {
		return "0".equals(resultCode) && "Imported".equals(accountStatus);
	}

}
