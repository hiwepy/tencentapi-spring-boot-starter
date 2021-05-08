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
import com.tencentcloud.spring.boot.tim.resp.account.AccountDeleteResponse;
import com.tencentcloud.spring.boot.tim.resp.account.AccountImportResponse;
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
	
}
