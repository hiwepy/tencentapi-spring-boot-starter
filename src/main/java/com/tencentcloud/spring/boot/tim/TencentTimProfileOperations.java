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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.UserProfilePortraitGetResponse;
import com.tencentcloud.spring.boot.tim.resp.UserProfilePortraitSetResponse;

/**
 * Tim 资料管理接口集成
 * https://cloud.tencent.com/document/product/269/42440
 */
public class TencentTimProfileOperations extends TencentTimOperations {

	public TencentTimProfileOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}

	/**
	 * 1、设置资料
	 * API：https://cloud.tencent.com/document/product/269/1640
	 * @param userId 业务用户ID
	 * @param nickname 用户昵称
	 * @param avatar 用户头像
	 * @return 操作结果
	 */
	public UserProfilePortraitSetResponse portraitSet(String userId, String nickname, String avatar) {
		Map<String, String> profile = new HashMap<>();
		profile.put("Tag_Profile_IM_Nick", nickname);
		profile.put("Tag_Profile_IM_Image", avatar);
		return this.portraitSet(userId, profile);
	}

	/**
	 * 2、设置资料
	 * API：https://cloud.tencent.com/document/product/269/1640
	 * @param userId 业务用户ID
	 * @param profile 用户资料
	 * @return 操作结果
	 */
	public UserProfilePortraitSetResponse portraitSet(String userId, Map<String, String> profile) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", String.valueOf(userId))
				.put("ProfileItem", profile.entrySet().stream().map(entry -> {
					HashMap<String, String> hashMap = new HashMap<>();
					hashMap.put("Tag", entry.getKey());
					hashMap.put("Value", entry.getValue());
					return hashMap;
				}).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.PORTRAIT_SET, requestBody, UserProfilePortraitSetResponse.class);
	}
	
	/**
	 * 3、拉取标配资料
	 * API：https://cloud.tencent.com/document/product/269/1639
	 * @param userIds 业务用户ID数组
	 * @return 操作结果
	 */
	public UserProfilePortraitGetResponse portraitGet(String... userIds) {
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
		return this.portraitGet(tagList, userIds);
	}
	
	/**
	 * 4、拉取资料
	 * API：https://cloud.tencent.com/document/product/269/1639
	 * @param tagList 指定要拉取的资料字段的 Tag，支持的字段有： 标配资料字段，自定义资料字段
	 * @param userIds 业务用户ID数组
	 * @return 操作结果
	 */
	public UserProfilePortraitGetResponse portraitGet(List<String> tagList, String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("TagList", tagList).build();
		return super.request(TimApiAddress.PORTRAIT_GET, requestBody, UserProfilePortraitGetResponse.class);
	}
	
	
}
