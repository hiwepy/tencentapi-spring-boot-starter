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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.AccountDeleteResponse;
import com.tencentcloud.spring.boot.tim.resp.AccountImportResponse;
import com.tencentcloud.spring.boot.tim.resp.AccountsImportResponse;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	 */
	public void asyncImport(String userId, String nickname, String avatar) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Identifier", getImUserByUserId(userId))
				.put("Nick", nickname)
				.put("FaceUrl", avatar).build();
		this.asyncRequest(TimApiAddress.MULTI_ACCOUNT_IMPORT.getValue() + joiner.join(getDefaultParams()), requestBody, (response) -> {
			if (response.isSuccessful()) {
                try {
					String content = response.body().string();
					AccountImportResponse res = getTimTemplate().toBean(content, AccountImportResponse.class);
					if (res.isSuccess()) {
						log.debug("导入单个帐号成功, response message is: {}", res);
					} else {
						log.error("导入单个帐号失败, response message is: {}", res);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		});
	}
	
	/**
	 * 2、导入多个帐号
	 * API：https://cloud.tencent.com/document/product/269/4919
	 * @param userIds 业务用户ID集合
	 */
	public void asyncImport(String[] userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Accounts", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList())).build();
		this.asyncRequest(TimApiAddress.MULTI_ACCOUNT_IMPORT.getValue() + joiner.join(getDefaultParams()), requestBody, (response) -> {
			if (response.isSuccessful()) {
                try {
					String content = response.body().string();
					AccountsImportResponse res = getTimTemplate().toBean(content, AccountsImportResponse.class);
					if (res.isSuccess()) {
						log.debug("导入多个帐号成功, response message is: {}", res);
					} else {
						log.error("导入多个帐号失败, response message is: {}", res);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		});
	}
	
	/**
	 * 3、删除帐号
	 * API：https://cloud.tencent.com/document/product/269/36443
	 * @param userIds 业务用户ID数组
	 */
	public void asyncDelete(String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("DeleteItem", Stream.of(userIds).map(uid -> {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("UserID", this.getImUserByUserId(uid));
					return userMap;
				}).collect(Collectors.toList())).build();
		this.asyncRequest(TimApiAddress.ACCOUNT_DELETE.getValue() + joiner.join(getDefaultParams()), requestBody, (response) -> {
			if (response.isSuccessful()) {
                try {
					String content = response.body().string();
					AccountDeleteResponse res = getTimTemplate().toBean(content, AccountDeleteResponse.class);
					if (res.isSuccess()) {
						log.debug("删除帐号成功, response message is: {}", res);
					} else {
						log.error("删除帐号失败, response message is: {}", res);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		});
	}
	
	/**
	 * 5、失效帐号登录态（踢出）
	 * API：https://cloud.tencent.com/document/product/269/3853
	 * @param userId 业务用户ID
	 */
	public void asyncKick(String userId) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Identifier", this.getImUserByUserId(userId)).build();
		this.asyncRequest(TimApiAddress.ACCOUNT_KICK.getValue() + joiner.join(getDefaultParams()), requestBody, (response) -> {
			if (response.isSuccessful()) {
                try {
					String content = response.body().string();
					TimActionResponse res = getTimTemplate().toBean(content, TimActionResponse.class);
					if (res.isSuccess()) {
						log.debug("失效帐号登录态（踢出）成功, response message is: {}", res);
					} else {
						log.error("失效帐号登录态（踢出）失败, response message is: {}", res);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		});
	}
	
}
