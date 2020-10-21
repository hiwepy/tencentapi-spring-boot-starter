/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
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
import com.tencentcloud.spring.boot.tim.resp.AccountCheckActionResponse;
import com.tencentcloud.spring.boot.tim.resp.AccountStateActionResponse;
import com.tencentcloud.spring.boot.tim.resp.IMActionResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Tim 接口集成 1、帐号管理
 * https://cloud.tencent.com/document/product/269/42440
 */
@Slf4j
public class TencentTimOpenimOperations extends TencentTimOperations {

	public TencentTimOpenimOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}

	/**
	 * 1、导入单个帐号 v4/im_open_login_svc/account_import
	 * API：https://cloud.tencent.com/document/product/269/1608
	 * @param userId
	 * @param nickname
	 * @param avatar
	 * @return
	 */
	public IMActionResponse accountImport(String userId, String nickname, String avatar) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Identifier", getImUserByUserId(userId))
				.put("Nick", nickname)
				.put("FaceUrl", avatar).build();
		IMActionResponse res = request(TimApiAddress.ACCOUNT_IMPORT.getValue() + joiner.join(getDefaultParams()),
				requestBody, IMActionResponse.class);
		System.out.println(res);
		if (!res.isSuccess()) {
			log.error("导入单个帐号失败, response message is: {}", res);
		}
		return res;
	}
	
	/**
	 * 2、导入多个帐号
	 * API：https://cloud.tencent.com/document/product/269/4919
	 * @param userIds
	 * @return
	 */
	public IMActionResponse accountImport(String[] userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Accounts", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList())).build();
		IMActionResponse res = request(TimApiAddress.MULTI_ACCOUNT_IMPORT.getValue() + joiner.join(getDefaultParams()),
				requestBody, IMActionResponse.class);
		System.out.println(res);
		if (!res.isSuccess()) {
			log.error("导入多个帐号失败, response message is: {}", res);
		}
		return res;
	}
	
	/**
	 * 3、删除帐号
	 * API：https://cloud.tencent.com/document/product/269/36443
	 * @param userIds
	 * @return
	 */
	public IMActionResponse accountDelete(String[] userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("DeleteItem", Stream.of(userIds).map(uid -> {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("UserID", this.getImUserByUserId(uid));
					return userMap;
				}).collect(Collectors.toList())).build();
		IMActionResponse res = request(TimApiAddress.ACCOUNT_DELETE.getValue() + joiner.join(getDefaultParams()),
				requestBody, IMActionResponse.class);
		System.out.println(res);
		if (!res.isSuccess()) {
			log.error("删除帐号失败, response message is: {}", res);
		}
		return res;
	}
	
	/**
	 * 4、查询帐号
	 * API：https://cloud.tencent.com/document/product/269/38417
	 * @param userIds
	 * @return
	 */
	public AccountCheckActionResponse accountCheck(String[] userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("CheckItem", Stream.of(userIds).map(uid -> {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("UserID", this.getImUserByUserId(uid));
					return userMap;
				}).collect(Collectors.toList())).build();
		AccountCheckActionResponse res = request(TimApiAddress.ACCOUNT_DELETE.getValue() + joiner.join(getDefaultParams()),
				requestBody, AccountCheckActionResponse.class);
		System.out.println(res);
		if (!res.isSuccess()) {
			log.error("查询帐号失败, response message is: {}", res);
		}
		return res;
	}
	
	/**
	 * 5、失效帐号登录态
	 * API：https://cloud.tencent.com/document/product/269/3853
	 * @param userId
	 * @return
	 */
	public IMActionResponse accountKick(String userId) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Identifier", this.getImUserByUserId(userId)).build();
		IMActionResponse res = request(TimApiAddress.ACCOUNT_KICK.getValue() + joiner.join(getDefaultParams()),
				requestBody, IMActionResponse.class);
		System.out.println(res);
		if (!res.isSuccess()) {
			log.error("查询失效帐号登录态失败, response message is: {}", res);
		}
		return res;
	}
	
	/**
	 * 6.1、查询帐号在线状态
	 * API：https://cloud.tencent.com/document/product/269/2566
	 * @param userIds
	 * @return
	 */
	public AccountStateActionResponse accountState(String[] userIds) {
		return this.accountState(userIds, false);
	}
	
	/**
	 * 6.2、查询帐号在线状态
	 * API：https://cloud.tencent.com/document/product/269/2566
	 * @param userIds
	 * @param needDetail
	 * @return
	 */
	public AccountStateActionResponse accountState(String[] userIds, boolean needDetail) {
		ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>()
			.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()));
		if(needDetail) {
			builder.put("IsNeedDetail", 1);
		}
		AccountStateActionResponse res = request(TimApiAddress.ACCOUNT_STATE.getValue() + joiner.join(getDefaultParams()),
				builder.build(), AccountStateActionResponse.class);
		System.out.println(res);
		if (!res.isSuccess()) {
			log.error("查询帐号在线状态失败, response message is: {}", res);
		}
		return res;
	}
	
}
