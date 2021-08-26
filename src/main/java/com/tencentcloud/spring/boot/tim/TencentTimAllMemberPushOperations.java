package com.tencentcloud.spring.boot.tim;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.tencentcloud.spring.boot.tim.req.message.MsgBody;
import com.tencentcloud.spring.boot.tim.req.message.OfflinePushInfo;
import com.tencentcloud.spring.boot.tim.req.push.Condition;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;
import com.tencentcloud.spring.boot.tim.resp.push.AllMemberPushResponse;
import com.tencentcloud.spring.boot.tim.resp.push.AppAttrNameResponse;
import com.tencentcloud.spring.boot.tim.resp.push.UserAttrs;
import com.tencentcloud.spring.boot.tim.resp.push.UserAttrsResponse;
import com.tencentcloud.spring.boot.tim.resp.push.UserTags;
import com.tencentcloud.spring.boot.tim.resp.push.UserTagsResponse;

/**
 * 3、全员推送
 * https://cloud.tencent.com/document/product/269/45933
 */
public class TencentTimAllMemberPushOperations extends TencentTimOperations {

	public TencentTimAllMemberPushOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}
	
	/**
	 * 1、全员推送
	 * API：https://cloud.tencent.com/document/product/269/45934
	 * @param userId 业务用户ID
	 * @param msgRandom 消息随机数，由随机函数产生。用于推送任务去重。对于不同的推送请求，MsgRandom7 天之内不能重复，否则视为相同的推送任务（调用推送 API 返回失败的时候可以用相同的 MsgRandom 进行重试）
	 * @param msgBody  消息内容，具体格式请参考 MsgBody 消息内容说明（一条消息可包括多种消息元素，所以 MsgBody 为 Array 类型）
	 * @return 操作结果
	 */
	public AllMemberPushResponse push(String userId, Integer msgRandom, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("MsgRandom", msgRandom)
				.put("MsgBody", msgBody)
				.build();
		return super.request(TimApiAddress.IM_PUSH, requestBody, AllMemberPushResponse.class);
	}
	
	/**
	 * 1、全员推送
	 * API：https://cloud.tencent.com/document/product/269/45934
	 * @param userId 业务用户ID
	 * @param msgRandom 消息随机数，由随机函数产生。用于推送任务去重。对于不同的推送请求，MsgRandom7 天之内不能重复，否则视为相同的推送任务（调用推送 API 返回失败的时候可以用相同的 MsgRandom 进行重试）
	 * @param msgLifeTime 消息离线存储时间，单位秒，最多保存7天（604800秒）。默认为0，表示不离线存储
	 * @param msgBody  消息内容，具体格式请参考 MsgBody 消息内容说明（一条消息可包括多种消息元素，所以 MsgBody 为 Array 类型）
	 * @return 操作结果
	 */
	public AllMemberPushResponse push(String userId, Integer msgRandom, Integer msgLifeTime, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("MsgRandom", msgRandom)
				.put("MsgLifeTime", Objects.isNull(msgLifeTime) ? 0 : msgLifeTime)
				.put("MsgBody", msgBody)
				.build();
		return super.request(TimApiAddress.IM_PUSH, requestBody, AllMemberPushResponse.class);
	}
	
	/**
	 * 1、全员推送
	 * API：https://cloud.tencent.com/document/product/269/45934
	 * @param userId 业务用户ID
	 * @param msgRandom 消息随机数，由随机函数产生。用于推送任务去重。对于不同的推送请求，MsgRandom7 天之内不能重复，否则视为相同的推送任务（调用推送 API 返回失败的时候可以用相同的 MsgRandom 进行重试）
	 * @param msgLifeTime 消息离线存储时间，单位秒，最多保存7天（604800秒）。默认为0，表示不离线存储
	 * @param offlinePushInfo 离线推送信息配置，具体可参考 消息格式描述
	 * @param msgBody  消息内容，具体格式请参考 MsgBody 消息内容说明（一条消息可包括多种消息元素，所以 MsgBody 为 Array 类型）
	 * @return 操作结果
	 */
	public AllMemberPushResponse push(String userId, Integer msgRandom, Integer msgLifeTime, OfflinePushInfo offlinePushInfo, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("MsgRandom", msgRandom)
				.put("MsgLifeTime", Objects.isNull(msgLifeTime) ? 0 : msgLifeTime)
				.put("MsgBody", msgBody)
				.put("OfflinePushInfo", offlinePushInfo)
				.build();
		return super.request(TimApiAddress.IM_PUSH, requestBody, AllMemberPushResponse.class);
	}
	
	/**
	 * 1、全员推送
	 * API：https://cloud.tencent.com/document/product/269/45934
	 * @param userId 业务用户ID
	 * @param msgRandom 消息随机数，由随机函数产生。用于推送任务去重。对于不同的推送请求，MsgRandom7 天之内不能重复，否则视为相同的推送任务（调用推送 API 返回失败的时候可以用相同的 MsgRandom 进行重试）
	 * @param msgLifeTime 消息离线存储时间，单位秒，最多保存7天（604800秒）。默认为0，表示不离线存储
	 * @param condition AttrsOr 和 AttrsAnd 可以并存，TagsOr 和 TagsAnd 也可以并存。但是标签和属性条件不能并存。如果没有 Condition，则推送给全部用户
	 * @param offlinePushInfo 离线推送信息配置，具体可参考 消息格式描述
	 * @param msgBody  消息内容，具体格式请参考 MsgBody 消息内容说明（一条消息可包括多种消息元素，所以 MsgBody 为 Array 类型）
	 * @return 操作结果
	 */
	public AllMemberPushResponse push(String userId, Integer msgRandom, Integer msgLifeTime, Condition condition, OfflinePushInfo offlinePushInfo, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("MsgRandom", msgRandom)
				.put("MsgLifeTime", Objects.isNull(msgLifeTime) ? 0 : msgLifeTime)
				.put("MsgBody", msgBody)
				.put("Condition", condition)
				.put("OfflinePushInfo", offlinePushInfo)
				.build();
		return super.request(TimApiAddress.IM_PUSH, requestBody, AllMemberPushResponse.class);
	}
	
	/**
	 * 2、设置应用属性名称
	 * API：https://cloud.tencent.com/document/product/269/45935
	 * @param attrNames 属性名数组，单个属性最长不超过50字节。应用最多可以有10个推送属性（编号从0到9），用户自定义每个属性的含义
	 * @return 操作结果
	 */
	public TimActionResponse setAppAttrNames(String ... attrNames) {
		Map<String, String> attrNameMap = Maps.newHashMap();
		for (int i = 0; i < attrNames.length; i++) {
			attrNameMap.put(String.valueOf(i), attrNames[i]);
		}
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("AttrNames", attrNameMap)
				.build();
		return super.request(TimApiAddress.IM_SET_ATTR_NAME, requestBody, TimActionResponse.class);
	}
	
	/**
	 * 3、获取应用属性名称
	 * API：https://cloud.tencent.com/document/product/269/45936
	 * @return 操作结果
	 */
	public AppAttrNameResponse getAppAttrNames() {
		return super.request(TimApiAddress.IM_GET_ATTR_NAME, Maps.newHashMap(), AppAttrNameResponse.class);
	}
	
	/**
	 * 4、设置用户属性
	 * API：https://cloud.tencent.com/document/product/269/45938
	 * @param userAttrs 属性名数组，单个属性最长不超过50字节。应用最多可以有10个推送属性（编号从0到9），用户自定义每个属性的含义
	 * @return 操作结果
	 */
	public TimActionResponse setUserAttrs(UserAttrs ... userAttrs) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("UserAttrs", userAttrs)
				.build();
		return super.request(TimApiAddress.IM_SET_ATTR, requestBody, TimActionResponse.class);
	}
	
	/**
	 * 5、获取用户属性
	 * API：https://cloud.tencent.com/document/product/269/45937
	 * @param userIds 用户ID数组
	 * @return 操作结果
	 */
	public UserAttrsResponse getUserAttrs(String ... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.IM_GET_ATTR, requestBody, UserAttrsResponse.class);
	}
	
	/**
	 * 6、删除用户属性
	 * API：https://cloud.tencent.com/document/product/269/45939
	 * @param userAttrs 属性名数组，单个属性最长不超过50字节。应用最多可以有10个推送属性（编号从0到9），用户自定义每个属性的含义
	 * @return 操作结果
	 */
	public TimActionResponse removeUserAttrs(UserAttrs ... userAttrs) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("UserAttrs", userAttrs)
				.build();
		return super.request(TimApiAddress.IM_REMOVE_ATTR, requestBody, TimActionResponse.class);
	}
	
	/**
	 * 7、添加用户标签
	 * API：https://cloud.tencent.com/document/product/269/45941
	 * a、每次请求最多只能给100个用户添加标签，请求体中单个用户添加标签数最多为10个。
	 * b、单个用户可设置最大标签数为100个，若用户当前标签超过100，则添加新标签之前请先删除旧标签。
	 * c、单个标签最大长度为50字节。
	 * @param userTags 用户标签数组
	 * @return 操作结果
	 */
	public TimActionResponse addUserTags(UserTags ... userTags) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("UserTags", userTags)
				.build();
		return super.request(TimApiAddress.IM_ADD_TAG, requestBody, TimActionResponse.class);
	}
	
	/**
	 * 8、获取用户标签
	 * API：https://cloud.tencent.com/document/product/269/45940
	 * @param userIds 用户ID数组
	 * @return 操作结果
	 */
	public UserTagsResponse getUserTags(String ... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.IM_GET_TAG, requestBody, UserTagsResponse.class);
	}
	
	/**
	 * 9、删除用户标签
	 * API：https://cloud.tencent.com/document/product/269/45942
	 * @param userTags 用户标签数组
	 * @return 操作结果
	 */
	public TimActionResponse removeUserTags(UserTags ... userTags) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("UserTags", userTags)
				.build();
		return super.request(TimApiAddress.IM_REMOVE_TAG, requestBody, TimActionResponse.class);
	}

	/**
	 * 10、删除用户所有标签
	 * API：https://cloud.tencent.com/document/product/269/45943
	 * @param userIds 用户ID数组
	 * @return 操作结果
	 */
	public TimActionResponse removeUserTags(String ... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.IM_REMOVE_ALL_TAGS, requestBody, TimActionResponse.class);
	}
	
}
