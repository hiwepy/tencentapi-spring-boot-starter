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
package com.tencentcloud.spring.boot.tim;

import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.ApiResponse;

public class TencentTimNospeakingAsyncOperations extends TencentTimSnsOperations {

	public TencentTimNospeakingAsyncOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}
	
	/**
	 * 1、设置全局禁言
	 * API：https://cloud.tencent.com/document/product/269/4230
	 * @param userId 设置禁言配置的帐号的业务用户ID
	 * @param c2CmsgNospeakingTime  单聊消息禁言时长，单位为秒，非负整数。等于 0 代表没有被设置禁言；等于最大值4294967295（十六进制 0xFFFFFFFF）代表被设置永久禁言；其它代表该帐号禁言时长，如果等于3600表示该帐号被禁言一小时
	 * @param groupmsgNospeakingTime 群组消息禁言时长，单位为秒，非负整数。等于0代表没有被设置禁言；等于最大值4294967295（十六进制 0xFFFFFFFF）代表被设置永久禁言；其它代表该帐号禁言时长，如果等于3600表示该帐号被禁言一小时
	 * @param consumer 响应处理回调函数
	 */
	public void asyncSetNoSpeaking(String userId, Integer c2CmsgNospeakingTime, Integer groupmsgNospeakingTime, Consumer<ApiResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Set_Account", this.getImUserByUserId(userId))
				.put("C2CmsgNospeakingTime", c2CmsgNospeakingTime)
				.put("GroupmsgNospeakingTime", groupmsgNospeakingTime)
				.build();
		this.asyncRequest(TimApiAddress.SET_NO_SPEAKING, requestBody, ApiResponse.class, consumer);
	}

}
