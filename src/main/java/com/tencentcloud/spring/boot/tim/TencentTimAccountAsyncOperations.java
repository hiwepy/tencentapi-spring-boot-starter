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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;
import com.tencentcloud.spring.boot.tim.resp.account.AccountCheckResponse;
import com.tencentcloud.spring.boot.tim.resp.account.AccountDeleteResponse;
import com.tencentcloud.spring.boot.tim.resp.account.AccountImportResponse;
import com.tencentcloud.spring.boot.tim.resp.account.AccountStateResponse;
import com.tencentcloud.spring.boot.tim.resp.account.AccountsImportResponse;

public class TencentTimAccountAsyncOperations extends TencentTimAccountOperations {

	public TencentTimAccountAsyncOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}
	
	/**
	 * 1、导入单个帐号
	 * API：https://cloud.tencent.com/document/product/269/1608
	 * @param userId 业务用户ID
	 * @param nickname 用户昵称
	 * @param avatar 用户头像
	 * @param consumer 响应处理回调函数
	 */
	public void asyncImport(String userId, String nickname, String avatar, Consumer<AccountImportResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Identifier", getImUserByUserId(userId))
				.put("Nick", nickname)
				.put("FaceUrl", avatar)
				.build();
		this.asyncRequest(TimApiAddress.MULTI_ACCOUNT_IMPORT, requestBody, AccountImportResponse.class, consumer);
	}
	
	/**
	 * 2、导入多个帐号
	 * API：https://cloud.tencent.com/document/product/269/4919
	 * @param userIds 业务用户ID集合
	 * @param consumer 响应处理回调函数
	 */
	public void asyncImport(String[] userIds, Consumer<AccountsImportResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Accounts", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList())).build();
		this.asyncRequest(TimApiAddress.MULTI_ACCOUNT_IMPORT, requestBody, AccountsImportResponse.class, consumer);
	}
	
	/**
	 * 3、删除帐号
	 * API：https://cloud.tencent.com/document/product/269/36443
	 * @param userIds 业务用户ID数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncDelete(String[] userIds, Consumer<AccountDeleteResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("DeleteItem", Stream.of(userIds).map(uid -> {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("UserID", this.getImUserByUserId(uid));
					return userMap;
				}).collect(Collectors.toList())).build();
		this.asyncRequest(TimApiAddress.ACCOUNT_DELETE, requestBody, AccountDeleteResponse.class, consumer);
	}
	
	/**
	 * 4、查询帐号
	 * API：https://cloud.tencent.com/document/product/269/38417
	 * @param userIds 业务用户ID数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncCheck(String[] userIds, Consumer<AccountCheckResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("CheckItem", Stream.of(userIds).map(uid -> {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("UserID", this.getImUserByUserId(uid));
					return userMap;
				}).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.ACCOUNT_CHECK, requestBody, AccountCheckResponse.class, consumer);
	}
	
	/**
	 * 5、失效帐号登录态（踢出）
	 * API：https://cloud.tencent.com/document/product/269/3853
	 * @param userId 业务用户ID
	 * @param consumer 响应处理回调函数
	 */
	public void asyncKickout(String userId, Consumer<TimActionResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Identifier", this.getImUserByUserId(userId))
				.build();
		this.asyncRequest(TimApiAddress.ACCOUNT_KICK, requestBody, TimActionResponse.class, consumer);
	}

	/**
	 * 6、查询帐号在线状态
	 * API：https://cloud.tencent.com/document/product/269/2566
	 * @param needDetail 是否需要详情结果
	 * @param userIds 业务用户ID数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetState(boolean needDetail, String[] userIds, Consumer<AccountStateResponse> consumer) {
		ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>()
			.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()));
		if(needDetail) {
			builder.put("IsNeedDetail", 1);
		}
		// {"ActionStatus":"OK","ErrorInfo":"","ErrorCode":0,"QueryResult":[{"To_Account":"449","State":"Offline","Status":"Offline"}]}
		this.asyncRequest(TimApiAddress.ACCOUNT_STATE, builder.build(), AccountStateResponse.class, consumer);
	}
	
}
