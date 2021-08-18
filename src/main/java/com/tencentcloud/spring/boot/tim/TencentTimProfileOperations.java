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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.req.profile.AdminForbidType;
import com.tencentcloud.spring.boot.tim.req.profile.AllowType;
import com.tencentcloud.spring.boot.tim.req.profile.GenderType;
import com.tencentcloud.spring.boot.tim.req.profile.TagProfile;
import com.tencentcloud.spring.boot.tim.resp.profile.UserProfilePortraitGetResponse;
import com.tencentcloud.spring.boot.tim.resp.profile.UserProfilePortraitSetResponse;

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
		return this.portraitSet(userId, nickname, null, avatar, null, null, null);
	}
	
	public UserProfilePortraitSetResponse portraitSet(String userId, String nickname, GenderType gender, String avatar, Integer birthDay, String signature) {
		return this.portraitSet(userId, nickname, gender, avatar, birthDay, signature, null);
	}
	
	public UserProfilePortraitSetResponse portraitSet(String userId, String nickname, GenderType gender, String avatar, Integer birthDay, String signature, Integer level) {
		Map<String, Object> profile = new HashMap<>();
		if(Objects.nonNull(nickname)) {
			profile.put(TagProfile.Tag_Profile_IM_Nick.getValue(), nickname);
		}
		if(Objects.nonNull(gender)) {
			profile.put(TagProfile.Tag_Profile_IM_Gender.getValue(), gender.getValue());
		}
		if(Objects.nonNull(avatar)) {
			profile.put(TagProfile.Tag_Profile_IM_Image.getValue(), avatar);
		}
		if(Objects.nonNull(birthDay)) {
			profile.put(TagProfile.Tag_Profile_IM_BirthDay.getValue(), birthDay);
		}
		if(Objects.nonNull(signature)) {
			profile.put(TagProfile.Tag_Profile_IM_SelfSignature.getValue(), signature);
		}
		if(Objects.nonNull(level)) {
			profile.put(TagProfile.Tag_Profile_IM_Level.getValue(), level);
		}
		return this.portraitSet(userId, profile);
	}
	
	public UserProfilePortraitSetResponse setNickname(String userId, String nickname) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_Nick.getValue(), nickname));
	}
	
	public UserProfilePortraitSetResponse setGender(String userId, GenderType gender) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_Gender.getValue(), gender.getValue()));
	}

	public UserProfilePortraitSetResponse setAvatar(String userId, String avatar) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_Image.getValue(), avatar));
	}

	public UserProfilePortraitSetResponse setImAllowType(String userId, AllowType allowType) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_AllowType.getValue(), allowType.getValue()));
	}
	
	public UserProfilePortraitSetResponse setBirthDay(String userId, Integer birthDay) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_BirthDay.getValue(), birthDay));
	}
	
	public UserProfilePortraitSetResponse setLocation(String userId, String location) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_Location.getValue(), location));
	}
	
	public UserProfilePortraitSetResponse setSelfSignature(String userId, String selfSignature) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_SelfSignature.getValue(), selfSignature));
	}
	
	public UserProfilePortraitSetResponse setLanguage(String userId, Integer language) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_Language.getValue(), language));
	}
	
	public UserProfilePortraitSetResponse setMsgSettings(String userId, Integer msgSettings) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_MsgSettings.getValue(), msgSettings));
	}
	
	public UserProfilePortraitSetResponse setAdminForbidType(String userId, AdminForbidType adminForbidType) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_AdminForbidType.getValue(), adminForbidType.getValue()));
	}
	
	public UserProfilePortraitSetResponse setLevel(String userId, Integer level) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_Level.getValue(), level));
	}
	
	public UserProfilePortraitSetResponse setRole(String userId, Integer role) {
		return this.portraitSet(userId, ImmutableMap.of(TagProfile.Tag_Profile_IM_Role.getValue(), role));
	}

	/**
	 * 2、设置资料
	 * API：https://cloud.tencent.com/document/product/269/1640
	 * @param userId 业务用户ID
	 * @param profile 用户资料
	 * @return 操作结果
	 */
	public UserProfilePortraitSetResponse portraitSet(String userId, Map<String, Object> profile) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("ProfileItem", profile.entrySet().stream().map(entry -> {
					HashMap<String, Object> hashMap = new HashMap<>();
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
		return this.portraitGet(TagProfile.asTagList(), userIds);
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
