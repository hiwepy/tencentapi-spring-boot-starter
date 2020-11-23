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

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.req.message.MsgBody;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Tim 单聊管理
 * https://cloud.tencent.com/document/product/269/42440
 */
@Slf4j
public class TencentTimOpenimOperations extends TencentTimOperations {

	public TencentTimOpenimOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}
	
	/**
	 * 1、单发单聊消息
	 * API：https://cloud.tencent.com/document/product/269/2282
	 * @param userId 业务用户ID
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public TimActionResponse sendMsg(String userId, MsgBody... msgBody) {
		String[] ForbidCallbackControl = new String[] {"ForbidBeforeSendMsgCallback",
		                                                "ForbidAfterSendMsgCallback"} ;
		
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("SyncOtherMachine", 1) // 消息不同步至发送方
				.put("To_Account", getImUserByUserId(userId))
				.put("MsgLifeTime", 60) // 消息保存60秒
				.put("MsgRandom", 1)
				.put("MsgTimeStamp", 1)
				.put("ForbidCallbackControl", ForbidCallbackControl)
				.put("MsgBody", msgBody).build();
		TimActionResponse res = request(TimApiAddress.SEND_MSG.getValue() + joiner.join(getDefaultParams()),
				requestBody, TimActionResponse.class);
		if (!res.isSuccess()) {
			log.error("导入单个帐号失败, response message is: {}", res);
		}
		return res;
	}
	
	public TimActionResponse sendMsg(String fromUid, String userId, MsgBody... msgBody) {
		String[] ForbidCallbackControl = new String[] {"ForbidBeforeSendMsgCallback",
		                                                "ForbidAfterSendMsgCallback"} ;
		
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("SyncOtherMachine", 1) // 消息不同步至发送方
				.put("From_Account", getImUserByUserId(fromUid))
				.put("To_Account", getImUserByUserId(userId))
				.put("MsgLifeTime", 60) // 消息保存60秒
				.put("MsgRandom", 1)
				.put("MsgTimeStamp", 1)
				.put("ForbidCallbackControl", ForbidCallbackControl)
				.put("MsgBody", msgBody).build();
		TimActionResponse res = request(TimApiAddress.SEND_MSG.getValue() + joiner.join(getDefaultParams()),
				requestBody, TimActionResponse.class);
		if (!res.isSuccess()) {
			log.error("导入单个帐号失败, response message is: {}", res);
		}
		return res;
	}
	
	
}
