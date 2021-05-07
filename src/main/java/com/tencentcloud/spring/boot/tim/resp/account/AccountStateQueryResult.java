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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AccountStateQueryResult {

	/**
	 * 返回的用户的 UserID
	 */
	@JsonProperty("To_Account")
	private String userId;

	/**
	 * 返回的用户状态，目前支持的状态有：
	 *前台运行状态（Online）：客户端登录后和即时通信 IM 后台有长连接
	 * 后台运行状态（PushOnline）：iOS 和 Android 进程被 kill 或因网络问题掉线，进入 PushOnline 状态，此时仍然可以接收消息的离线推送。客户端切到后台，但是进程未被手机操作系统 kill 掉时，此时状态仍是 Online
	 *未登录状态（Offline）：客户端主动退出登录或者客户端自上一次登录起7天之内未登录过
	 *如果用户是多终端登录，则只要有一个终端的状态是 Online ，该字段值就是 Online
	 */
	@JsonProperty("State")
	private String state;
	@JsonProperty("Status")
	private String status;
	
	/**
	 * 详细的登录平台信息
	 */
	@JsonProperty("Detail")
	private List<AccountStateQueryResultDetail> details;
	
}
