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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.profile.UserProfilePortraitGetResponse;
import com.tencentcloud.spring.boot.tim.resp.profile.UserProfilePortraitSetResponse;

public class TencentTimProfileAsyncOperations extends TencentTimProfileOperations {

	public TencentTimProfileAsyncOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}

	/**
	 * 1、设置资料
	 * API：https://cloud.tencent.com/document/product/269/1640
	 * @param userId 业务用户ID
	 * @param nickname 用户昵称
	 * @param avatar 用户头像
	 * @param consumer 响应处理回调函数
	 */
	public void asyncPortraitSet(String userId, String nickname, String avatar, Consumer<UserProfilePortraitSetResponse> consumer) {
		Map<String, Object> profile = new HashMap<>();
		profile.put("Tag_Profile_IM_Nick", nickname);
		profile.put("Tag_Profile_IM_Image", avatar);
		this.asyncPortraitSet(userId, profile, consumer);
	}
	
	/**
	 * 2、设置资料
	 * API：https://cloud.tencent.com/document/product/269/1640
	 * @param userId 业务用户ID
	 * @param profile 用户资料
	 * @param consumer 响应处理回调函数
	 */
	public void asyncPortraitSet(String userId, Map<String, Object> profile, Consumer<UserProfilePortraitSetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", String.valueOf(userId))
				.put("ProfileItem", profile.entrySet().stream().map(entry -> {
					HashMap<String, Object> hashMap = new HashMap<>();
					hashMap.put("Tag", entry.getKey());
					hashMap.put("Value", entry.getValue());
					return hashMap;
				}).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.PORTRAIT_SET, requestBody, UserProfilePortraitSetResponse.class, consumer);
	}
	
	/**
	 * 3、拉取标配资料
	 * API：https://cloud.tencent.com/document/product/269/1639
	 * @param userIds 业务用户ID数组
	 * @param consumer 结果处理回调
	 */
	public void asyncPortraitGet(String[] userIds, Consumer<UserProfilePortraitGetResponse> consumer ) {
		
		List<String> tagList = new ArrayList<String>();
		tagList.add("Tag_Profile_IM_Nick");
		tagList.add("Tag_Profile_IM_Gender");
		tagList.add("Tag_Profile_IM_BirthDay");
		tagList.add("Tag_Profile_IM_Location");
		tagList.add("Tag_Profile_IM_SelfSignature");
		tagList.add("Tag_Profile_IM_AllowType");
		tagList.add("Tag_Profile_IM_Language");
		tagList.add("Tag_Profile_IM_MsgSettings");
		tagList.add("Tag_Profile_IM_AdminForbidType");
		tagList.add("Tag_Profile_IM_Level");
		tagList.add("Tag_Profile_IM_Role");
		
		this.asyncPortraitGet(userIds, tagList, consumer);
	}
	
	/**
	 * 3、拉取资料
	 * API：https://cloud.tencent.com/document/product/269/1639
	 * @param userIds 业务用户ID数组
	 * @param tagList 指定要拉取的资料字段的 Tag，支持的字段有： 标配资料字段，自定义资料字段
	 * @param consumer 结果处理回调
	 */
	public void asyncPortraitGet(String[] userIds, List<String> tagList, Consumer<UserProfilePortraitGetResponse> consumer ) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("TagList", tagList)
				.build();
		this.asyncRequest(TimApiAddress.PORTRAIT_GET, requestBody, UserProfilePortraitGetResponse.class, consumer);
	}
	
}
