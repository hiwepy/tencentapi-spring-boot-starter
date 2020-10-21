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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.IMActionResponse;
import com.tencentcloud.spring.boot.tim.resp.UserProfileItemResponse;

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

	public UserProfileItemResponse portraitGet(String userId) {
		ArrayList<String> tagList = new ArrayList<String>();
		tagList.add("Tag_Profile_IM_Nick");
		tagList.add("Tag_Profile_IM_Image");
		ArrayList<String> toAccount = new ArrayList<String>();
		toAccount.add(userId);
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>().put("To_Account", toAccount)
				.put("TagList", tagList).build();
		UserProfileItemResponse res = request(
				TimApiAddress.PORTRAIT_GET.getValue() + joiner.join(getDefaultParams()), requestBody,
				UserProfileItemResponse.class);
		if (!res.isSuccess()) {
			log.error("获取信息失败, response message is: {}", res);
		}
		return res;
	}

	/**
	 * 设置资料
	 *
	 * @param userId
	 * @param nickname
	 * @param avatar
	 * @return
	 */
	public IMActionResponse portraitSet(Long userId, String nickname, String avatar) {
		IMActionResponse res = new IMActionResponse();
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
				IMActionResponse.class);
		if (!res.isSuccess()) {
			log.error("设置资料失败, response message is: {}", res);
		}
		return res;
	}
	
}
