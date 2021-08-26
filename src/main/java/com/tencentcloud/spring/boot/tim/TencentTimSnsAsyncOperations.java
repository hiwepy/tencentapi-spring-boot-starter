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
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.req.sns.FriendAddItem;
import com.tencentcloud.spring.boot.tim.req.sns.FriendImportItem;
import com.tencentcloud.spring.boot.tim.req.sns.FriendUpdateItem;
import com.tencentcloud.spring.boot.tim.resp.sns.BlacklistAddResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.BlacklistCheckResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.BlacklistDeleteResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.BlacklistGetResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendAddResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendCheckResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendDeleteAllResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendDeleteResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendGetListResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendGetResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendImportResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendUpdateResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.GroupAddResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.GroupDeleteResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.GroupGetResponse;

public class TencentTimSnsAsyncOperations extends TencentTimSnsOperations {

	public TencentTimSnsAsyncOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}

	/**
	 * 1、添加好友
	 * API：https://cloud.tencent.com/document/product/269/1643
	 * @param userId 业务用户ID
	 * @param addType  加好友方式（默认双向加好友方式）：Add_Type_Single 表示单向加好友, Add_Type_Both 表示双向加好友
	 * @param forceAdd 是否强制相互添加好友
	 * @param friends 添加的好友数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncAddFriend(String userId, String addType, boolean forceAdd, FriendAddItem[] friends, Consumer<FriendAddResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("AddFriendItem", Stream.of(friends).map(friend -> {
					friend.setAccount(this.getImUserByUserId(friend.getAccount()));
					return friend;
				}).collect(Collectors.toList()))
				.put("AddType", addType)
				.put("ForceAddFlags", forceAdd ? 1 : 0)
				.build();
		this.asyncRequest(TimApiAddress.FRIEND_ADD, requestBody, FriendAddResponse.class, consumer);
	}
	
	/**
	 * 2、导入好友
	 * API：https://cloud.tencent.com/document/product/269/8301
	 * @param userId 业务用户ID
	 * @param friends 导入的好友数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncImportFriend(String userId, FriendImportItem[] friends, Consumer<FriendImportResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("AddFriendItem", Stream.of(friends).map(friend -> {
					friend.setAccount(this.getImUserByUserId(friend.getAccount()));
					return friend;
				}).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.FRIEND_IMPORT, requestBody, FriendImportResponse.class, consumer);
	}
	

	/**
	 * 3、更新好友
	 * API：https://cloud.tencent.com/document/product/269/12525
	 * @param userId 业务用户ID
	 * @param friends 需要更新的好友对象数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncUpdateFriend(String userId, FriendUpdateItem[] friends, Consumer<FriendUpdateResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("UpdateItem", Stream.of(friends).map(friend -> {
					friend.setAccount(this.getImUserByUserId(friend.getAccount()));
					return friend;
				}).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.FRIEND_UPDATE, requestBody, FriendUpdateResponse.class, consumer);
	}
	
	/**
	 * 4、删除好友
	 * API：https://cloud.tencent.com/document/product/269/1644
	 * @param userId 业务用户ID
	 * @param deleteType 删除模式；
	 * 	单向删除好友 	Delete_Type_Single 	只将 To_Account 从 From_Account 的好友表中删除，但不会将 From_Account 从 To_Account 的好友表中删除
	 * 	双向删除好友 	Delete_Type_Both 	将 To_Account 从 From_Account 的好友表中删除，同时将 From_Account 从 To_Account 的好友表中删除
	 * @param friends 待删除的好友的 UserID 列表，单次请求的 To_Account 数不得超过1000
	 * @param consumer 响应处理回调函数
	 */
	public void asyncDeleteFriend(String userId, String deleteType, String[] friends, Consumer<FriendDeleteResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("To_Account", Stream.of(friends).map(friend -> {
					return this.getImUserByUserId(friend);
				}).collect(Collectors.toList()))
				.put("DeleteType", deleteType)
				.build();
		this.asyncRequest(TimApiAddress.FRIEND_DELETE, requestBody, FriendDeleteResponse.class, consumer);
	}

	/**
	 * 5、删除所有好友
	 * API：https://cloud.tencent.com/document/product/269/1645
	 * @param userId 业务用户ID
	 * @param deleteType 删除模式；
	 * 	单向删除好友 	Delete_Type_Single 	只将 To_Account 从 From_Account 的好友表中删除，但不会将 From_Account 从 To_Account 的好友表中删除
	 * 	双向删除好友 	Delete_Type_Both 	将 To_Account 从 From_Account 的好友表中删除，同时将 From_Account 从 To_Account 的好友表中删除
	 * @param consumer 响应处理回调函数
	 */
	public void asyncDeleteAllFriend(String userId, String deleteType, Consumer<FriendDeleteAllResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("DeleteType", deleteType)
				.build();
		this.asyncRequest(TimApiAddress.FRIEND_DELETE_ALL, requestBody, FriendDeleteAllResponse.class, consumer);
	}
	
	/**
	 * 6、校验好友
	 * API：https://cloud.tencent.com/document/product/269/1646
	 * @param userId 业务用户ID
	 * @param checkType 校验模式； https://cloud.tencent.com/document/product/269/1501#.E6.A0.A1.E9.AA.8C.E5.A5.BD.E5.8F.8B
	 * @param friends 待删除的好友的 UserID 列表，单次请求的 To_Account 数不得超过1000
	 * @param consumer 响应处理回调函数
	 */
	public void asyncCheckFriend(String userId, String checkType ,String[] friends, Consumer<FriendCheckResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("To_Account", Stream.of(friends).map(friend -> {
					return this.getImUserByUserId(friend);
				}).collect(Collectors.toList()))
				.put("CheckType", checkType)
				.build();
		this.asyncRequest(TimApiAddress.FRIEND_CHECK, requestBody, FriendCheckResponse.class, consumer);
	}
	
	/**
	 * 7、拉取好友
	 * API：https://cloud.tencent.com/document/product/269/1647
	 * @param userId 业务用户ID
	 * @param startIndex 分页的起始位置
	 * @param standardSequence 上次拉好友数据时返回的 StandardSequence，如果 StandardSequence 字段的值与后台一致，后台不会返回标配好友数据
	 * @param customSequence 上次拉好友数据时返回的 CustomSequence，如果 CustomSequence 字段的值与后台一致，后台不会返回自定义好友数据
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetFriends(String userId, Integer startIndex, Integer standardSequence, Integer customSequence, Consumer<FriendGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("StartIndex", startIndex)
				.put("StandardSequence", standardSequence)
				.put("CustomSequence", customSequence)
				.build();
		this.asyncRequest(TimApiAddress.FRIEND_GET, requestBody, FriendGetResponse.class, consumer);
	}
	
	/**
	 * 7、拉取好友
	 * API：https://cloud.tencent.com/document/product/269/1647
	 * @param fromUserId 业务用户ID
	 * @param userIds 业务用户ID数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetFriends(String fromUserId, String[] userIds, Consumer<FriendGetListResponse> consumer) {
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
		this.asyncGetFriends(fromUserId, tagList, userIds, consumer);
	}
	
	/**
	 * 8、拉取好友
	 * API：https://cloud.tencent.com/document/product/269/1647
	 * @param fromUserId 业务用户ID
	 * @param tagList 指定要拉取的资料字段的 Tag，支持的字段有： 标配资料字段，自定义资料字段
	 * @param userIds 业务用户ID数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetFriends(String fromUserId, List<String> tagList, String[] userIds, Consumer<FriendGetListResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("TagList", tagList)
				.build();
		this.asyncRequest(TimApiAddress.FRIEND_GET, requestBody, FriendGetListResponse.class, consumer);
	}

	/**
	 * 9、添加黑名单
	 * API：https://cloud.tencent.com/document/product/269/3718
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param fromUserId 需要添加该 UserID 的黑名单
	 * @param userIds 待添加的黑名单的 UserID 列表，单次请求的 To_Account 数不得超过1000
	 * @param consumer 响应处理回调函数
	 */
	public void asyncAddBlackList(String fromUserId, String[] userIds, Consumer<BlacklistAddResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.BLACK_LIST_ADD, requestBody, BlacklistAddResponse.class, consumer);
	}
	
	/**
	 * 10、删除黑名单
	 * API：https://cloud.tencent.com/document/product/269/3719
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param fromUserId 需要删除该 UserID 的黑名单
	 * @param userIds 待删除的黑名单的 UserID 列表，单次请求的 To_Account 数不得超过1000
	 * @param consumer 响应处理回调函数
	 */
	public void asyncDeleteBlackList(String fromUserId, String[] userIds, Consumer<BlacklistDeleteResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.BLACK_LIST_DELETE, requestBody, BlacklistDeleteResponse.class, consumer);
	}
	
	/**
	 * 11、拉取黑名单
	 * API：https://cloud.tencent.com/document/product/269/3722
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param userId 需要拉取该 UserID 的黑名单
	 * @param startIndex 拉取的起始位置
	 * @param maxLimited 每页最多拉取的黑名单数
	 * @param lastSequence 上一次拉黑名单时后台返回给客户端的 Seq，初次拉取时为0
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetBlackList(String userId, Integer startIndex, Integer maxLimited, Integer lastSequence, Consumer<BlacklistGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("StartIndex", startIndex)
				.put("MaxLimited", maxLimited)
				.put("LastSequence", lastSequence)
				.build();
		this.asyncRequest(TimApiAddress.BLACK_LIST_GET, requestBody, BlacklistGetResponse.class, consumer);
	}
	
	/**
	 * 12、校验黑名单
	 * API：https://cloud.tencent.com/document/product/269/3725
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param checkType 校验模式，详情可参见 校验黑名单
	 * @param fromUserId 需要校验该 UserID 的黑名单
	 * @param userIds 待校验的黑名单的 UserID 列表，单次请求的 To_Account 数不得超过1000
	 * @param consumer 响应处理回调函数
	 */
	public void asyncCheckBlackList(String checkType, String fromUserId, String[] userIds, Consumer<BlacklistCheckResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("CheckType", checkType)
				.build();
		this.asyncRequest(TimApiAddress.BLACK_LIST_CHECK, requestBody, BlacklistCheckResponse.class, consumer);
	}
	
	/**
	 * 12、添加分组
	 * API：https://cloud.tencent.com/document/product/269/10107
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param fromUserId 需要为该 UserID 添加新分组
	 * @param groupNames 新增分组列表
	 * @param consumer 响应处理回调函数
	 */
	public void asyncAddGroup(String fromUserId, String[] groupNames, Consumer<GroupAddResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("GroupName", groupNames)
				.build();
		this.asyncRequest(TimApiAddress.GROUP_ADD, requestBody, GroupAddResponse.class, consumer);
	}
	
	/**
	 * 12、添加分组
	 * API：https://cloud.tencent.com/document/product/269/10107
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param fromUserId 需要为该 UserID 添加新分组
	 * @param groupNames 新增分组列表
	 * @param userIds 需要加入新增分组的好友的 UserID 列表
	 * @param consumer 响应处理回调函数
	 */
	public void asyncAddGroup(String fromUserId, String[] groupNames, String[] userIds, Consumer<GroupAddResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("GroupName", groupNames)
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.GROUP_ADD, requestBody, GroupAddResponse.class, consumer);
	}
	
	/**
	 * 13、删除分组
	 * API：https://cloud.tencent.com/document/product/269/3719
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param fromUserId 需要删除该 UserID 的黑名单
	 * @param groupNames 要删除的分组列表
	 * @param consumer 响应处理回调函数
	 */
	public void asyncDeleteGroups(String fromUserId, String[] groupNames, Consumer<GroupDeleteResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("GroupName", groupNames)
				.build();
		this.asyncRequest(TimApiAddress.GROUP_DELETE, requestBody, GroupDeleteResponse.class, consumer);
	}
	
	/**
	 * 14、拉取分组
	 * API：https://cloud.tencent.com/document/product/269/3722
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param userId 指定要拉取分组的用户的 UserID
	 * @param lastSequence 上一次拉取分组时后台返回给客户端的 Seq，初次拉取时为0，只有 GroupName 为空时有效
	 * @param groupNames 要拉取的分组名称
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetGroups(String userId, Integer lastSequence, String[] groupNames, Consumer<GroupGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("LastSequence", lastSequence)
				.put("GroupName", groupNames)
				.build();
		this.asyncRequest(TimApiAddress.GROUP_GET, requestBody, GroupGetResponse.class, consumer);
	}
	
	/**
	 * 15、拉取分组
	 * API：https://cloud.tencent.com/document/product/269/3722
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param userId 指定要拉取分组的用户的 UserID
	 * @param lastSequence 上一次拉取分组时后台返回给客户端的 Seq，初次拉取时为0，只有 GroupName 为空时有效
	 * @param needFriend 是否需要拉取分组下的 User 列表, Need_Friend_Type_Yes: 需要拉取, 不填时默认不拉取, 只有 GroupName 为空时有效
	 * @param groupNames 要拉取的分组名称
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetGroups(String userId, Integer lastSequence, String needFriend, String[] groupNames, Consumer<GroupGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("LastSequence", lastSequence)
				.put("NeedFriend", needFriend)
				.put("GroupName", groupNames)
				.build();
		this.asyncRequest(TimApiAddress.GROUP_GET, requestBody, GroupGetResponse.class, consumer);
	}
	
}
