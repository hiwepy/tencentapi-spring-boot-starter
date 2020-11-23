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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.UserProfilePortraitGetResponse;
import com.tencentcloud.spring.boot.tim.resp.UserProfilePortraitSetResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Tim 资料管理接口集成
 * https://cloud.tencent.com/document/product/269/42440
 */
@Slf4j
public class TencentTimProfileOperations extends TencentTimOperations {

	public TencentTimProfileOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}

	
	public UserProfilePortraitGetResponse portraitGet(String... userIds) {
		ArrayList<String> tagList = new ArrayList<String>();
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
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("TagList", tagList).build();
		UserProfilePortraitGetResponse res = request(
				TimApiAddress.PORTRAIT_GET.getValue() + joiner.join(getDefaultParams()), requestBody,
				UserProfilePortraitGetResponse.class);
		if (!res.isSuccess()) {
			log.error("获取信息失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
	}

	/**
	 * 设置资料
	 * @param userId 业务用户ID
	 * @param nickname 用户昵称
	 * @param avatar 用户头像
	 * @return 操作结果
	 */
	public UserProfilePortraitSetResponse portraitSet(Long userId, String nickname, String avatar) {
		UserProfilePortraitSetResponse res = new UserProfilePortraitSetResponse();
		if (userId == null || userId <= 0) {
			return res;
		}
		if (StringUtils.isBlank(nickname) && StringUtils.isBlank(avatar)) {
			return res;
		}
		ArrayList<HashMap<String, String>> objects = new ArrayList<>();
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("Tag", "Tag_Profile_IM_Nick");
		hashMap.put("Value", nickname);
		HashMap<String, String> hashMap1 = new HashMap<>();
		hashMap1.put("Tag", "Tag_Profile_IM_Image");
		hashMap1.put("Value", avatar);
		objects.add(hashMap);
		objects.add(hashMap1);

		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", String.valueOf(userId)).put("ProfileItem", objects).build();
		res = request(TimApiAddress.PORTRAIT_SET.getValue() + joiner.join(getDefaultParams()), requestBody,
				UserProfilePortraitSetResponse.class);
		if (!res.isSuccess()) {
			log.error("设置资料失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
	}
	
}
