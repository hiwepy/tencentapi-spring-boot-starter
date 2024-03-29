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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.req.message.BatchMessage;
import com.tencentcloud.spring.boot.tim.req.message.Message;
import com.tencentcloud.spring.boot.tim.req.message.MsgBody;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;
import com.tencentcloud.spring.boot.tim.resp.message.MessageGetResponse;
import com.tencentcloud.spring.boot.tim.resp.message.MessageSendResponse;

/**
 * 2、单聊信息
 * https://cloud.tencent.com/document/product/269/2282
 */
public class TencentTimOpenimOperations extends TencentTimOperations {

	public static final String[] FORBID_CALLBACK_CONTROL = new String[] { "ForbidBeforeSendMsgCallback",
			"ForbidAfterSendMsgCallback" };
	
	public TencentTimOpenimOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}
	
	/**
	 * 1、单发单聊消息
	 * a、管理员向帐号发消息，接收方看到消息发送者是管理员。
     * b、管理员指定某一帐号向其他帐号发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * c、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
	 * API：https://cloud.tencent.com/document/product/269/2282
	 * @param userId 业务用户ID
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(String userId, MsgBody... msgBody) {
		return this.sendMsg(userId, false, msgBody);
	}
	
	/**
	 * 1、单发单聊消息
	 * a、管理员向帐号发消息，接收方看到消息发送者是管理员。
     * b、管理员指定某一帐号向其他帐号发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * c、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
	 * API：https://cloud.tencent.com/document/product/269/2282
	 * @param userId 业务用户ID
	 * @param syncOtherMachine 是否希望将消息同步至 From_Account
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(String userId, boolean syncOtherMachine, MsgBody... msgBody) {
		return this.sendMsg(userId, syncOtherMachine, FORBID_CALLBACK_CONTROL, msgBody);
	}
	
	/**
	 * 1、单发单聊消息
	 * a、管理员向帐号发消息，接收方看到消息发送者是管理员。
     * b、管理员指定某一帐号向其他帐号发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * c、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
	 * API：https://cloud.tencent.com/document/product/269/2282
	 * @param userId 业务用户ID
	 * @param syncOtherMachine 是否希望将消息同步至 From_Account
	 * @param forbidCallbackControl 消息回调禁止开关，只对本条消息有效，ForbidBeforeSendMsgCallback 表示禁止发消息前回调，ForbidAfterSendMsgCallback 表示禁止发消息后回调
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(String userId, boolean syncOtherMachine, String[] forbidCallbackControl, MsgBody... msgBody) {
		
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("SyncOtherMachine", syncOtherMachine ? 1 : 2) // 消息不同步至发送方
				.put("To_Account", getImUserByUserId(userId))
				.put("MsgLifeTime", getTimTemplate().getMsgLifeTime())
				.put("MsgRandom", RandomUtils.nextInt())
				.put("MsgTimeStamp", Long.valueOf(System.currentTimeMillis() /1000L).intValue())
				.put("ForbidCallbackControl", forbidCallbackControl)
				.put("MsgBody", msgBody).build();

		return super.request(TimApiAddress.SEND_MSG, requestBody, MessageSendResponse.class);
	}
	
	/**
	 * 1、单发单聊消息
	 * a、管理员向帐号发消息，接收方看到消息发送者是管理员。
     * b、管理员指定某一帐号向其他帐号发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * c、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
	 * API：https://cloud.tencent.com/document/product/269/2282
	 * @param fromUid 发送方用户ID
	 * @param userId 业务用户ID
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(String fromUid, String userId, MsgBody... msgBody) {
		return this.sendMsg(fromUid, userId, false, msgBody);
	}

	/**
	 * 1、单发单聊消息
	 * a、管理员向帐号发消息，接收方看到消息发送者是管理员。
     * b、管理员指定某一帐号向其他帐号发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * c、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
	 * API：https://cloud.tencent.com/document/product/269/2282
	 * @param fromUid 发送方用户ID
	 * @param userId 业务用户ID
	 * @param syncOtherMachine 是否希望将消息同步至 From_Account
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(String fromUid, String userId, boolean syncOtherMachine, MsgBody... msgBody) {
		return this.sendMsg(fromUid, userId, syncOtherMachine, FORBID_CALLBACK_CONTROL, msgBody);
	}
	
	/**
	 * 1、单发单聊消息
	 * a、管理员向帐号发消息，接收方看到消息发送者是管理员。
     * b、管理员指定某一帐号向其他帐号发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * c、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
	 * API：https://cloud.tencent.com/document/product/269/2282
	 * @param fromUid 发送方用户ID
	 * @param userId 业务用户ID
	 * @param syncOtherMachine 是否希望将消息同步至 From_Account
	 * @param forbidCallbackControl 消息回调禁止开关，只对本条消息有效，ForbidBeforeSendMsgCallback 表示禁止发消息前回调，ForbidAfterSendMsgCallback 表示禁止发消息后回调
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(String fromUid, String userId, boolean syncOtherMachine, String[] forbidCallbackControl, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("SyncOtherMachine", syncOtherMachine ? 1 : 2) // 若不希望将消息同步至 From_Account，则 SyncOtherMachine 填写2；若希望将消息同步至 From_Account，则 SyncOtherMachine 填写1。
				.put("From_Account", getImUserByUserId(fromUid))
				.put("To_Account", getImUserByUserId(userId))
				.put("MsgLifeTime", getTimTemplate().getMsgLifeTime())
				.put("MsgRandom", RandomUtils.nextInt())
				.put("MsgTimeStamp", Long.valueOf(System.currentTimeMillis() /1000L).intValue())
				.put("ForbidCallbackControl", FORBID_CALLBACK_CONTROL)
				.put("MsgBody", msgBody)
				.build();
		return super.request(TimApiAddress.SEND_MSG, requestBody, MessageSendResponse.class);
	}
	
	/**
	 * 1、单发单聊消息
	 * a、管理员向帐号发消息，接收方看到消息发送者是管理员。
     * b、管理员指定某一帐号向其他帐号发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * c、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
	 * API：https://cloud.tencent.com/document/product/269/2282
	 * @param message 消息实体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(Message message) {
		return super.request(TimApiAddress.SEND_MSG, message, MessageSendResponse.class);
	}
	
	/**
	 * 2、批量发单聊消息
	 * a、支持一次对最多500个用户进行单发消息。
     * b、与单发消息相比，该接口更适用于营销类消息、系统通知 tips 等时效性较强的消息。
     * c、管理员指定某一帐号向目标帐号批量发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * d、该接口不触发回调请求。
     * e、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
     * API：https://cloud.tencent.com/document/product/269/1612
	 * @param userIds 业务用户ID
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(List<String> userIds, MsgBody... msgBody) {
		return this.sendMsg(userIds, false, msgBody);
	}
	
	/**
	 * 2、批量发单聊消息
	 * a、支持一次对最多500个用户进行单发消息。
     * b、与单发消息相比，该接口更适用于营销类消息、系统通知 tips 等时效性较强的消息。
     * c、管理员指定某一帐号向目标帐号批量发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * d、该接口不触发回调请求。
     * e、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
     * API：https://cloud.tencent.com/document/product/269/1612
	 * @param userIds 业务用户ID
	 * @param syncOtherMachine 是否希望将消息同步至 From_Account
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(List<String> userIds, boolean syncOtherMachine, MsgBody... msgBody) {
		return this.sendMsg(userIds, syncOtherMachine, FORBID_CALLBACK_CONTROL, msgBody);
	}
	
	/**
	 * 2、批量发单聊消息
	 * a、支持一次对最多500个用户进行单发消息。
     * b、与单发消息相比，该接口更适用于营销类消息、系统通知 tips 等时效性较强的消息。
     * c、管理员指定某一帐号向目标帐号批量发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * d、该接口不触发回调请求。
     * e、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
     * API：https://cloud.tencent.com/document/product/269/1612
	 * @param userIds 业务用户ID
	 * @param syncOtherMachine 是否希望将消息同步至 From_Account
	 * @param forbidCallbackControl 消息回调禁止开关，只对本条消息有效，ForbidBeforeSendMsgCallback 表示禁止发消息前回调，ForbidAfterSendMsgCallback 表示禁止发消息后回调
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(List<String> userIds, boolean syncOtherMachine, String[] forbidCallbackControl, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("SyncOtherMachine", syncOtherMachine ? 1 : 2) // 消息不同步至发送方
				.put("To_Account", userIds.stream().map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("MsgLifeTime", getTimTemplate().getMsgLifeTime())
				.put("MsgRandom", RandomUtils.nextInt())
				.put("MsgTimeStamp", Long.valueOf(System.currentTimeMillis() /1000L).intValue())
				.put("ForbidCallbackControl", forbidCallbackControl)
				.put("MsgBody", msgBody)
				.build();
		return super.request(TimApiAddress.SEND_BATCH_MSG, requestBody, MessageSendResponse.class);
	}
	
	/**
	 * 2、批量发单聊消息
	 * a、支持一次对最多500个用户进行单发消息。
     * b、与单发消息相比，该接口更适用于营销类消息、系统通知 tips 等时效性较强的消息。
     * c、管理员指定某一帐号向目标帐号批量发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * d、该接口不触发回调请求。
     * e、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
     * API：https://cloud.tencent.com/document/product/269/1612
	 * @param fromUid 发送方用户ID
	 * @param userIds 业务用户ID
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(String fromUid, List<String> userIds, MsgBody... msgBody) {
		return this.sendMsg(fromUid, userIds, false, msgBody);
	}

	/**
	 * 2、批量发单聊消息
	 * a、支持一次对最多500个用户进行单发消息。
     * b、与单发消息相比，该接口更适用于营销类消息、系统通知 tips 等时效性较强的消息。
     * c、管理员指定某一帐号向目标帐号批量发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * d、该接口不触发回调请求。
     * e、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
     * API：https://cloud.tencent.com/document/product/269/1612
	 * @param fromUid 发送方用户ID
	 * @param userIds 业务用户ID
	 * @param syncOtherMachine 是否希望将消息同步至 From_Account
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(String fromUid, List<String> userIds, boolean syncOtherMachine, MsgBody... msgBody) {
		return this.sendMsg(fromUid, userIds, syncOtherMachine, FORBID_CALLBACK_CONTROL, msgBody);
	}
	
	/**
	 * 2、批量发单聊消息
	 * a、支持一次对最多500个用户进行单发消息。
     * b、与单发消息相比，该接口更适用于营销类消息、系统通知 tips 等时效性较强的消息。
     * c、管理员指定某一帐号向目标帐号批量发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * d、该接口不触发回调请求。
     * e、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
     * API：https://cloud.tencent.com/document/product/269/1612
	 * @param fromUid 发送方用户ID
	 * @param userIds 业务用户ID
	 * @param syncOtherMachine 是否希望将消息同步至 From_Account
	 * @param forbidCallbackControl 消息回调禁止开关，只对本条消息有效，ForbidBeforeSendMsgCallback 表示禁止发消息前回调，ForbidAfterSendMsgCallback 表示禁止发消息后回调
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(String fromUid, List<String> userIds, boolean syncOtherMachine, String[] forbidCallbackControl, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("SyncOtherMachine", syncOtherMachine ? 1 : 2) // 若不希望将消息同步至 From_Account，则 SyncOtherMachine 填写2；若希望将消息同步至 From_Account，则 SyncOtherMachine 填写1。
				.put("From_Account", getImUserByUserId(fromUid))
				.put("To_Account", userIds.stream().map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("MsgLifeTime", getTimTemplate().getMsgLifeTime())
				.put("MsgRandom", RandomUtils.nextInt())
				.put("MsgTimeStamp", Long.valueOf(System.currentTimeMillis() /1000L).intValue())
				.put("ForbidCallbackControl", FORBID_CALLBACK_CONTROL)
				.put("MsgBody", msgBody)
				.build();
		return super.request(TimApiAddress.SEND_BATCH_MSG, requestBody, MessageSendResponse.class);
	}
	
	/**
	 * 2、批量发单聊消息
	 * a、支持一次对最多500个用户进行单发消息。
     * b、与单发消息相比，该接口更适用于营销类消息、系统通知 tips 等时效性较强的消息。
     * c、管理员指定某一帐号向目标帐号批量发消息，接收方看到发送者不是管理员，而是管理员指定的帐号。
     * d、该接口不触发回调请求。
     * e、该接口不会检查发送者和接收者的好友关系（包括黑名单），同时不会检查接收者是否被禁言。
     * API：https://cloud.tencent.com/document/product/269/1612
	 * @param message 消息实体
	 * @return 操作结果
	 */
	public MessageSendResponse sendMsg(BatchMessage message) {
		return super.request(TimApiAddress.SEND_BATCH_MSG, message, MessageSendResponse.class);
	}
    
	/**
	 * 3、导入单聊消息
	 * a、导入历史单聊消息到即时通信 IM。
     * b、平滑过渡期间，将原有即时通信实时单聊消息导入到即时通信 IM。
     * c、该接口不会触发回调。
     * d、该接口会根据 From_Account ， To_Account ， MsgRandom ， MsgTimeStamp 字段的值对导入的消息进行去重。仅当这四个字段的值都对应相同时，才判定消息是重复的，消息是否重复与消息内容本身无关。
     * e、重复导入的消息不会覆盖之前已导入的消息（即消息内容以首次导入的为准）。
     * API：https://cloud.tencent.com/document/product/269/2568
	 * @param fromUid 发送方用户ID
	 * @param userId 业务用户ID
	 * @param syncFromOldSystem 是否历史消息导入,该字段只能填1或2，其他值是非法值；1表示实时消息导入，消息加入未读计数；2表示历史消息导入，消息不计入未读
	 * @param msgBody 消息体
	 * @return 操作结果
	 */
	public TimActionResponse importMsg(String fromUid, String userId, boolean syncFromOldSystem,  MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("SyncFromOldSystem", syncFromOldSystem ? 1 : 2)
				.put("From_Account", getImUserByUserId(fromUid))
				.put("To_Account", this.getImUserByUserId(userId))
				.put("MsgRandom", RandomUtils.nextInt())
				.put("MsgTimeStamp", Long.valueOf(System.currentTimeMillis() /1000L).intValue())
				.put("MsgBody", msgBody)
				.build();
		return super.request(TimApiAddress.IMPORT_MSG, requestBody, TimActionResponse.class);
	}
	
	/**
	 * 4、查询单聊消息
     * API：https://cloud.tencent.com/document/product/269/42794
	 * @param fromUid 发送方用户ID
	 * @param userId 接收方用户ID
	 * @param maxCnt 是否历史消息导入,该字段只能填1或2，其他值是非法值；1表示实时消息导入，消息加入未读计数；2表示历史消息导入，消息不计入未读
	 * @param minTime 请求的消息时间范围的最小值
	 * @param maxTime 请求的消息时间范围的最大值
	 * @return 操作结果
	 */
	public MessageGetResponse getMsgs(String fromUid, String userId, int maxCnt, int minTime, int maxTime) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", getImUserByUserId(fromUid))
				.put("To_Account", getImUserByUserId(userId))
				.put("MaxCnt", maxCnt)
				.put("MinTime", minTime)
				.put("MaxTime", maxTime)
				.build();
		return super.request(TimApiAddress.ADMIN_GET_ROAMMSG, requestBody, MessageGetResponse.class);
	}
	
	/**
	 * 4、查询单聊消息
     * API：https://cloud.tencent.com/document/product/269/42794
	 * @param fromUid 发送方用户ID
	 * @param userId 接收方用户ID
	 * @param maxCnt 是否历史消息导入,该字段只能填1或2，其他值是非法值；1表示实时消息导入，消息加入未读计数；2表示历史消息导入，消息不计入未读
	 * @param minTime 请求的消息时间范围的最小值
	 * @param maxTime 请求的消息时间范围的最大值
	 * @param lastMsgKey 上一次拉取到的最后一条消息的 MsgKey，续拉时需要填该字段，填写方法见上方 
	 * @return 操作结果
	 */
	public MessageGetResponse getMsgs(String fromUid, String userId, int maxCnt, int minTime, int maxTime, String lastMsgKey) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", getImUserByUserId(fromUid))
				.put("To_Account", getImUserByUserId(userId))
				.put("MaxCnt", maxCnt)
				.put("MinTime", minTime)
				.put("MaxTime", maxTime)
				.put("LastMsgKey", lastMsgKey)
				.build();
		return super.request(TimApiAddress.ADMIN_GET_ROAMMSG, requestBody, MessageGetResponse.class);
	}
	
	/**
	 * 5、撤回单聊消息
     * API：https://cloud.tencent.com/document/product/269/38980
	 * @param fromUid 发送方用户ID
	 * @param userId 接收方用户ID
	 * @param msgKey 待撤回消息的唯一标识。该字段由 REST API 接口 单发单聊消息 和 批量发单聊消息 返回
	 * @return 操作结果
	 */
	public TimActionResponse withdrawMsg(String fromUid, String userId, String msgKey) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", getImUserByUserId(fromUid))
				.put("To_Account", getImUserByUserId(userId))
				.put("MsgKey", msgKey)
				.build();
		return super.request(TimApiAddress.ADMIN_MSG_WITHDRAW, requestBody, TimActionResponse.class);
	}
	
	/**
	 * 6、设置单聊消息已读
     * API：https://cloud.tencent.com/document/product/269/50349
	 * @param reportUid 进行消息已读的用户 UserId
	 * @param peerUid 进行消息已读的单聊会话的另一方用户 UserId
	 * @return 操作结果
	 */
	public TimActionResponse readMsg(String reportUid, String peerUid) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Report_Account", getImUserByUserId(reportUid))
				.put("Peer_Account", getImUserByUserId(peerUid))
				.build();
		return super.request(TimApiAddress.ADMIN_SET_MSG_READ, requestBody, TimActionResponse.class);
	}
	
}
