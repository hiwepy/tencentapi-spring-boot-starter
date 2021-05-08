package com.tencentcloud.spring.boot.tim;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.ApiResponse;
import com.tencentcloud.spring.boot.tim.resp.NoSpeakingResponse;

/**
 * 全局禁言管理
 * https://cloud.tencent.com/document/product/269/1519
 */
public class TencentTimNospeakingOperations extends TencentTimOperations {

	public TencentTimNospeakingOperations(TencentTimTemplate timTemplate) {
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
	public ApiResponse setNoSpeaking(String userId, Integer c2CmsgNospeakingTime, Integer groupmsgNospeakingTime) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Set_Account", this.getImUserByUserId(userId))
				.put("C2CmsgNospeakingTime", c2CmsgNospeakingTime)
				.put("GroupmsgNospeakingTime", groupmsgNospeakingTime)
				.build();
		return super.request(TimApiAddress.SET_NO_SPEAKING, requestBody, ApiResponse.class);
	}
	
	/**
	 * 2、查询全局禁言
	 * API：https://cloud.tencent.com/document/product/269/4229
	 * @param userId 查询禁言信息的帐号的业务用户ID
	 * @return 操作结果
	 */
	public NoSpeakingResponse getNoSpeaking(String userId) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Get_Account", this.getImUserByUserId(userId))
				.build();
		return super.request(TimApiAddress.GET_NO_SPEAKING, requestBody, NoSpeakingResponse.class);
	}
	
}
