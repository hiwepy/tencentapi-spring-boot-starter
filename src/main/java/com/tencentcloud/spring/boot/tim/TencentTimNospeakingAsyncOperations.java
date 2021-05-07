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

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	 * @return 操作结果
	 */
	public void asyncSetNoSpeaking(String userId, Integer c2CmsgNospeakingTime, Integer groupmsgNospeakingTime) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Set_Account", this.getImUserByUserId(userId))
				.put("C2CmsgNospeakingTime", c2CmsgNospeakingTime)
				.put("GroupmsgNospeakingTime", groupmsgNospeakingTime)
				.build();
		this.asyncRequest(TimApiAddress.SET_NO_SPEAKING.getValue() + joiner.join(getDefaultParams()), requestBody, (response) -> {
			if (response.isSuccessful()) {
                try {
					String content = response.body().string();
					ApiResponse res = getTimTemplate().toBean(content, ApiResponse.class);
					if (res.isSuccess()) {
						log.debug("设置全局禁言成功, response message is: {}", res);
					} else {
						log.error("设置全局禁言失败, response message is: {}", res);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		});
	}

}
