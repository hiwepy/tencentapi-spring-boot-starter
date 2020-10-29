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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.AccountCheckResponse;
import com.tencentcloud.spring.boot.tim.resp.AccountDeleteResponse;
import com.tencentcloud.spring.boot.tim.resp.AccountImportResponse;
import com.tencentcloud.spring.boot.tim.resp.AccountKickResponse;
import com.tencentcloud.spring.boot.tim.resp.AccountStateResponse;
import com.tencentcloud.spring.boot.tim.resp.AccountsImportResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Tim 账号管理
 * https://cloud.tencent.com/document/product/269/42440
 */
@Slf4j
public class TencentTimAccountOperations extends TencentTimOperations {

	public TencentTimAccountOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}

	/**
	 * 1、导入单个帐号
	 * API：https://cloud.tencent.com/document/product/269/1608
	 * @param userId
	 * @param nickname
	 * @param avatar
	 * @return
	 */
	public AccountImportResponse aImport(String userId, String nickname, String avatar) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Identifier", getImUserByUserId(userId))
				.put("Nick", nickname)
				.put("FaceUrl", avatar).build();
		AccountImportResponse res = request(TimApiAddress.ACCOUNT_IMPORT.getValue() + joiner.join(getDefaultParams()),
				requestBody, AccountImportResponse.class);
		if (!res.isSuccess()) {
			log.error("导入单个帐号失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
	}
	
	/**
	 * 2、导入多个帐号
	 * API：https://cloud.tencent.com/document/product/269/4919
	 * @param userIds
	 * @return
	 */
	public AccountsImportResponse aImport(String[] userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Accounts", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList())).build();
		AccountsImportResponse res = request(TimApiAddress.MULTI_ACCOUNT_IMPORT.getValue() + joiner.join(getDefaultParams()),
				requestBody, AccountsImportResponse.class);
		if (!res.isSuccess()) {
			log.error("导入多个帐号失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
	}
	
	/**
	 * 3、删除帐号
	 * API：https://cloud.tencent.com/document/product/269/36443
	 * @param userIds
	 * @return
	 */
	public AccountDeleteResponse delete(String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("DeleteItem", Stream.of(userIds).map(uid -> {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("UserID", this.getImUserByUserId(uid));
					return userMap;
				}).collect(Collectors.toList())).build();
		AccountDeleteResponse res = request(TimApiAddress.ACCOUNT_DELETE.getValue() + joiner.join(getDefaultParams()),
				requestBody, AccountDeleteResponse.class);
		if (!res.isSuccess()) {
			log.error("删除帐号失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
	}
	
	/**
	 * 4、查询帐号
	 * API：https://cloud.tencent.com/document/product/269/38417
	 * @param userIds
	 * @return
	 */
	public AccountCheckResponse check(String[] userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("CheckItem", Stream.of(userIds).map(uid -> {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("UserID", this.getImUserByUserId(uid));
					return userMap;
				}).collect(Collectors.toList())).build();
		AccountCheckResponse res = request(TimApiAddress.ACCOUNT_CHECK.getValue() + joiner.join(getDefaultParams()),
				requestBody, AccountCheckResponse.class);
		if (!res.isSuccess()) {
			log.error("查询帐号失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
	}
	
	/**
	 * 5、失效帐号登录态（踢出）
	 * API：https://cloud.tencent.com/document/product/269/3853
	 * @param userId
	 * @return
	 */
	public AccountKickResponse kick(String userId) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Identifier", this.getImUserByUserId(userId)).build();
		AccountKickResponse res = request(TimApiAddress.ACCOUNT_KICK.getValue() + joiner.join(getDefaultParams()),
				requestBody, AccountKickResponse.class);
		if (!res.isSuccess()) {
			log.error("查询失效帐号登录态失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
	}
	
	/**
	 * 6.1、查询帐号在线状态
	 * API：https://cloud.tencent.com/document/product/269/2566
	 * @param userIds
	 * @return
	 */
	public AccountStateResponse onlineState(String... userIds) {
		return this.onlineState(userIds, false);
	}
	
	/**
	 * 6.2、查询帐号在线状态
	 * API：https://cloud.tencent.com/document/product/269/2566
	 * @param userIds
	 * @param needDetail
	 * @return
	 */
	public AccountStateResponse onlineState(String[] userIds, boolean needDetail) {
		ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>()
			.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()));
		if(needDetail) {
			builder.put("IsNeedDetail", 1);
		}
		AccountStateResponse res = request(TimApiAddress.ACCOUNT_STATE.getValue() + joiner.join(getDefaultParams()),
				builder.build(), AccountStateResponse.class);
		System.out.println(res);
		if (!res.isSuccess()) {
			log.error("查询帐号在线状态失败，ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
	}
	
}
