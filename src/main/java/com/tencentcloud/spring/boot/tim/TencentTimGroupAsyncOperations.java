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
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.req.common.MapKV;
import com.tencentcloud.spring.boot.tim.req.group.GroupInfo;
import com.tencentcloud.spring.boot.tim.req.group.GroupJoinedQuery;
import com.tencentcloud.spring.boot.tim.req.group.GroupMember;
import com.tencentcloud.spring.boot.tim.req.group.GroupMemberImport;
import com.tencentcloud.spring.boot.tim.req.group.GroupMemberModify;
import com.tencentcloud.spring.boot.tim.req.group.GroupMemberQuery;
import com.tencentcloud.spring.boot.tim.req.group.GroupMessage;
import com.tencentcloud.spring.boot.tim.req.group.GroupMessageImport;
import com.tencentcloud.spring.boot.tim.req.group.GroupModify;
import com.tencentcloud.spring.boot.tim.req.group.GroupResponseFilter;
import com.tencentcloud.spring.boot.tim.req.message.MsgBody;
import com.tencentcloud.spring.boot.tim.req.message.OfflinePushInfo;
import com.tencentcloud.spring.boot.tim.resp.group.AppGroupGetResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupChangeOwnerResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupCreateResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupDestroyResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupInfoGetResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupJoinedListGetResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMemberAccount;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMemberAddResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMemberDeleteResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMemberForbidSendMsgResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMemberGetResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMemberImportResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMemberModifyResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMemberOnlineNumGetResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMemberRoleGetResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMemberShuttedUinResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupModifyResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMsgGetResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMsgImportResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMsgRecallResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMsgSendResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupMsgUnreadNumResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupSystemNotificationSendResponse;

public class TencentTimGroupAsyncOperations extends TencentTimGroupOperations {

	public TencentTimGroupAsyncOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}
	
	/**
	 * 1、获取 App 中的所有群组
	 * API：https://cloud.tencent.com/document/product/269/1614
	 * @param limit 本次获取的群组 ID 数量的上限，不得超过 10000。如果不填，默认为最大值 10000
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetAppGroupList(Integer limit, Consumer<AppGroupGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Limit", limit)
				.build();
		this.asyncRequest(TimApiAddress.GET_APPID_GROUP_LIST, requestBody, AppGroupGetResponse.class, consumer);
	}
	
	/**
	 * 2、获取 App 中的所有群组
	 * API：https://cloud.tencent.com/document/product/269/1614
	 * @param limit 本次获取的群组 ID 数量的上限，不得超过 10000。如果不填，默认为最大值 10000
	 * @param next  群太多时分页拉取标志，第一次填0，以后填上一次返回的值，返回的 Next 为0代表拉完了
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetAppGroupList(Integer limit, Integer next, Consumer<AppGroupGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Limit", limit)
				.put("Next", next)
				.build();
		this.asyncRequest(TimApiAddress.GET_APPID_GROUP_LIST, requestBody, AppGroupGetResponse.class, consumer);
	}

	/**
	 * 3、获取 App 中的所有群组
	 * API：https://cloud.tencent.com/document/product/269/1614
	 * @param limit 本次获取的群组 ID 数量的上限，不得超过 10000。如果不填，默认为最大值 10000
	 * @param next  群太多时分页拉取标志，第一次填0，以后填上一次返回的值，返回的 Next 为0代表拉完了
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetAppGroupList(Integer limit, Integer next, String groupType, Consumer<AppGroupGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Limit", limit)
				.put("Next", next)
				.put("GroupType", groupType)
				.build();
		this.asyncRequest(TimApiAddress.GET_APPID_GROUP_LIST, requestBody, AppGroupGetResponse.class, consumer);
	}
	
	/**
	 * 4、创建群组
	 * API：https://cloud.tencent.com/document/product/269/1615
	 * @param ownerId 业务用户ID
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param consumer 响应处理回调函数
	 */
	public void asyncCreateGroup(String ownerId, String groupType, String groupName, 
			Consumer<GroupCreateResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Owner_Account", this.getImUserByUserId(ownerId))
				.put("Type", groupType)
				.put("Name", groupName)
				.build();
		this.asyncRequest(TimApiAddress.CREATE_GROUP, requestBody, GroupCreateResponse.class, consumer);
	}
	
	/**
	 * 5、创建群组，并指定群简介、群公告等群基础信息
	 * API：https://cloud.tencent.com/document/product/269/1615
	 * @param ownerId 业务用户ID
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param introduction 群简介，最长240字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param notification  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param faceUrl  群头像 URL，最长100字节
	 * @param maxMemberCount  最大群成员数量，缺省时的默认值：私有群是200，公开群是2000，聊天室是6000，音视频聊天室和在线成员广播大群无限制
	 * @param applyJoinOption  申请加群处理方式。包含 FreeAccess（自由加入），NeedPermission（需要验证），DisableApply（禁止加群），不填默认为 NeedPermission（需要验证）仅当创建支持申请加群的 群组 时，该字段有效
	 * @param consumer 响应处理回调函数
	 */
	public void asyncCreateGroup(String ownerId, String groupType, String groupName, String introduction,
			String notification, String faceUrl, Integer maxMemberCount, String applyJoinOption,
			Consumer<GroupCreateResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Owner_Account", this.getImUserByUserId(ownerId))
				.put("Type", groupType)
				.put("Name", groupName)
				.put("Introduction", introduction)
				.put("Notification", notification)
				.put("FaceUrl", faceUrl)
				.put("MaxMemberCount", maxMemberCount)
				.put("ApplyJoinOption", applyJoinOption)
				.build();
		this.asyncRequest(TimApiAddress.CREATE_GROUP, requestBody, GroupCreateResponse.class, consumer);
	}
	
	/**
	 * 6、创建群组，并指定初始化群成员列表，群成员列表在请求包说明表中有描述。
	 * API：https://cloud.tencent.com/document/product/269/1615
	 * @param ownerId 业务用户ID
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param groupMembers  初始群成员列表，最多500个（选填）
	 * @param consumer 响应处理回调函数
	 */
	public void asyncCreateGroupWithMembers(String ownerId, String groupType, String groupName, GroupMember[] groupMembers, 
			Consumer<GroupCreateResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Owner_Account", this.getImUserByUserId(ownerId))
				.put("Type", groupType)
				.put("Name", groupName)
				.put("MemberList", groupMembers)
				.build();
		this.asyncRequest(TimApiAddress.CREATE_GROUP, requestBody, GroupCreateResponse.class, consumer);
	}
	
	/**
	 * 7、创建群组，并指定自定义群组 ID
	 * API：https://cloud.tencent.com/document/product/269/1615
	 * @param ownerId 业务用户ID
	 * @param groupId 用户自定义群组ID
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param groupMembers  初始群成员列表，最多500个（选填）
	 * @param consumer 响应处理回调函数
	 */
	public void asyncCreateGroupWithMembers(String ownerId, String groupId, String groupType, String groupName, GroupMember[] groupMembers, 
			Consumer<GroupCreateResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Owner_Account", this.getImUserByUserId(ownerId))
				.put("Type", groupType)
				.put("GroupId", groupId)
				.put("Name", groupName)
				.put("MemberList", groupMembers)
				.build();
		this.asyncRequest(TimApiAddress.CREATE_GROUP, requestBody, GroupCreateResponse.class, consumer);
	}
	
	/**
	 * 8、创建群组
	 * API：https://cloud.tencent.com/document/product/269/1615
	 * @param group 群组信息
	 * @param consumer 响应处理回调函数
	 */
	public void asyncCreateGroup(GroupInfo group, Consumer<GroupCreateResponse> consumer) {
		this.asyncRequest(TimApiAddress.CREATE_GROUP, group, GroupCreateResponse.class, consumer);
	}
	
	/**
	* 9、修改群基础资料
	 * API：https://cloud.tencent.com/document/product/269/1620
	 * @param groupId 需要修改基础信息的群组的 ID
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param introduction 群简介，最长240字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param notification  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param faceUrl  群头像 URL，最长100字节
	 * @param maxMemberCount  最大群成员数量，缺省时的默认值：私有群是200，公开群是2000，聊天室是6000，音视频聊天室和在线成员广播大群无限制
	 * @param applyJoinOption  申请加群处理方式。包含 FreeAccess（自由加入），NeedPermission（需要验证），DisableApply（禁止加群），不填默认为 NeedPermission（需要验证）仅当创建支持申请加群的 群组 时，该字段有效
	 * @param consumer 响应处理回调函数
	 */
	public void asyncUpdateGroup(String groupId, String groupName, String introduction,
			String notification, String faceUrl, Integer maxMemberCount, String applyJoinOption, 
			Consumer<GroupModifyResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Name", groupName)
				.put("Introduction", introduction)
				.put("Notification", notification)
				.put("FaceUrl", faceUrl)
				.put("MaxMemberCount", maxMemberCount)
				.put("ApplyJoinOption", applyJoinOption)
				.build();
		this.asyncRequest(TimApiAddress.MODIFY_GROUP_BASE_INFO, requestBody, GroupModifyResponse.class, consumer);
	}
	
	/**
	 * 10、修改群基础资料
	 * API：https://cloud.tencent.com/document/product/269/1620
	 * @param group 修改内容
	 * @param consumer 响应处理回调函数
	 */
	public void asyncUpdateGroup(GroupModify group, Consumer<GroupModifyResponse> consumer) {
		this.asyncRequest(TimApiAddress.MODIFY_GROUP_BASE_INFO, group, GroupModifyResponse.class, consumer);
	}
	
	/**
	 * 11、获取群详细资料
	 * API：https://cloud.tencent.com/document/product/269/1616
	 * @param groupIds 群组列表（必填）
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetGroupInfo(String[] groupIds, Consumer<GroupInfoGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupIdList", groupIds)
				.build();
		this.asyncRequest(TimApiAddress.GET_GROUP_INFO, requestBody, GroupInfoGetResponse.class, consumer);
	}
	
	/**
	 * 12、获取群详细资料
	 * API：https://cloud.tencent.com/document/product/269/1616
	 * @param groupIds 群组列表（必填）
	 * @param responseFilter 包含三个过滤器：GroupBaseInfoFilter，MemberInfoFilter，AppDefinedDataFilter_Group，分别是基础信息字段过滤器，成员信息字段过滤器，群组维度的自定义字段过滤器
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetGroupInfo(String[] groupIds, GroupResponseFilter responseFilter, Consumer<GroupInfoGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupIdList", groupIds)
				.put("ResponseFilter", responseFilter)
				.build();
		this.asyncRequest(TimApiAddress.GET_GROUP_INFO, requestBody, GroupInfoGetResponse.class, consumer);
	}
	
	/**
	 * 12、获取群成员详细资料
	 * API：https://cloud.tencent.com/document/product/269/1617
	 * @param groupId 群组ID（必填）
	 * @param silence 是否静默加人。0：非静默加人；1：静默加人。不填该字段默认为0
	 * @param userIds 待删除的群成员
	 * @param consumer 响应处理回调函数
	 */
	public void asyncAddGroupMember(String groupId, Integer silence, String[] userIds, Consumer<GroupMemberAddResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Silence", Objects.nonNull(silence) ? silence : 0)
				.put("MemberList", Stream.of(userIds).map(uid -> {
					return GroupMemberAccount.builder().account(this.getImUserByUserId(uid));
				}).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.ADD_GROUP_MEMBER, requestBody, GroupMemberAddResponse.class, consumer);
	}
	
	/**
	 * 13、删除群成员
	 * API：https://cloud.tencent.com/document/product/269/1622
	 * @param groupId 群组ID（必填）
	 * @param reason 踢出用户原因
	 * @param userIds 待添加的群成员数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncDeleteGroupMember(String groupId, String reason, String[] userIds, Consumer<GroupMemberDeleteResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Reason", reason)
				.put("MemberToDel_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.DELETE_GROUP_MEMBER, requestBody, GroupMemberDeleteResponse.class, consumer);
	}
	
	/**
	 * 14、删除群成员
	 * API：https://cloud.tencent.com/document/product/269/1622
	 * @param groupId 群组ID（必填）
	 * @param silence 是否静默删人。0表示非静默删人，1表示静默删人。静默即删除成员时不通知群里所有成员，只通知被删除群成员。不填写该字段时默认为0
	 * @param reason 踢出用户原因
	 * @param userIds 待添加的群成员数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncDeleteGroupMember(String groupId, Integer silence, String reason,  String[] userIds, Consumer<GroupMemberDeleteResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Silence", Objects.nonNull(silence) ? silence : 0)
				.put("Reason", reason)
				.put("MemberToDel_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.DELETE_GROUP_MEMBER, requestBody, GroupMemberDeleteResponse.class, consumer);
	}
	
	/**
	 * 15、修改群成员资料-设置/取消指定群成员的管理员身份
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param groupId 群组ID（必填）
	 * @param userId 要操作的群成员ID（必填）
	 * @param role 成员身份，Admin/Member 分别为设置/取消管理员
	 * @param consumer 响应处理回调函数
	 */
	public void asyncUpdateGroupMemberRole(String groupId, String userId, String role, Consumer<GroupMemberModifyResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("Role", role)
				.build();
		this.asyncRequest(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, requestBody, GroupMemberModifyResponse.class, consumer);
	}
	

	/**
	 * 16、修改群成员资料-设置成员消息屏蔽位
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param groupId 群组ID（必填）
	 * @param userId 要操作的群成员ID（必填）
	 * @param msgFlag 消息屏蔽类型： AcceptAndNotify 代表接收并提示消息，Discard 代表不接收也不提示消息，AcceptNotNotify 代表接收消息但不提示。
	 * @param consumer 响应处理回调函数
	 */
	public void asyncUpdateGroupMemberMsgFlag(String groupId, String userId, String msgFlag, Consumer<GroupMemberModifyResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("MsgFlag", msgFlag)
				.build();
		this.asyncRequest(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, requestBody, GroupMemberModifyResponse.class, consumer);
	}
	
	/**
	 * 17、修改群成员资料-设置成员的群名片
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param groupId 群组ID（必填）
	 * @param userId 要操作的群成员ID（必填）
	 * @param nameCard 群名片（最大不超过50个字节）
	 * @param consumer 响应处理回调函数
	 */
	public void asyncUpdateGroupMemberNameCard(String groupId, String userId, String nameCard, Consumer<GroupMemberModifyResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("NameCard", nameCard)
				.build();
		this.asyncRequest(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, requestBody, GroupMemberModifyResponse.class, consumer);
	}

	/**
	 * 18、修改群成员资料-设置成员自定义字段
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param groupId 群组ID（必填）
	 * @param userId 要操作的群成员ID（必填）
	 * @param appMemberDefinedData 群成员维度的自定义字段，默认情况是没有的，可以通过 即时通信 IM 控制台 进行配置，详情请参阅 群组系统
	 * @param consumer 响应处理回调函数
	 */
	public void asyncUpdateGroupMemberAttrs(String groupId, String userId, List<MapKV> appMemberDefinedData,
			Consumer<GroupMemberModifyResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("AppMemberDefinedData", appMemberDefinedData)
				.build();
		this.asyncRequest(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, requestBody, GroupMemberModifyResponse.class, consumer);
	}
	
	/**
	 * 19、修改群成员资料-设置群成员禁言时间
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param groupId 群组ID（必填）
	 * @param userId 要操作的群成员ID（必填）
	 * @param shutUpTime 群成员的禁言时间，单位为秒，0表示取消禁言
	 * @param consumer 响应处理回调函数
	 */
	public void asyncUpdateGroupMemberShutUpTime(String groupId, String userId, Integer shutUpTime, 
			Consumer<GroupMemberModifyResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("ShutUpTime", Objects.nonNull(shutUpTime) ? shutUpTime : 0)
				.build();
		this.asyncRequest(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, requestBody, GroupMemberModifyResponse.class, consumer);
	}
	
	/**
	 * 20、修改群成员资料
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param member 群成员资料
	 * @param consumer 响应处理回调函数
	 */
	public void asyncUpdateGroupMember(GroupMemberModify member, Consumer<GroupMemberModifyResponse> consumer) {
		this.asyncRequest(TimApiAddress.GET_GROUP_MEMBER_INFO, member, GroupMemberModifyResponse.class, consumer);
	}
	
	/**
	 * 21、获取群成员详细资料
	 * API：https://cloud.tencent.com/document/product/269/1617
	 * @param groupId 群组ID（必填）
	 * @param limit 一次最多获取多少个成员的资料，不得超过6000。如果不填，则获取群内全部成员的信息
	 * @param offset 从第几个成员开始获取，如果不填则默认为0，表示从第一个成员开始获取   
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetGroupMember(String groupId, Integer limit, Integer offset, Consumer<GroupMemberGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Limit", limit)
				.put("Offset", offset)
				.build();
		this.asyncRequest(TimApiAddress.GET_GROUP_MEMBER_INFO, requestBody, GroupMemberGetResponse.class, consumer);
	}
	
	/**
	 * 22、获取群成员详细资料
	 * API：https://cloud.tencent.com/document/product/269/1617
	 * @param query 筛选条件
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetGroupMember(GroupMemberQuery query, Consumer<GroupMemberGetResponse> consumer) {
		this.asyncRequest(TimApiAddress.GET_GROUP_MEMBER_INFO, query, GroupMemberGetResponse.class, consumer);
	}
	
	/**
	 * 23、解散群组（群组解散之后将无法恢复，请谨慎调用该接口）
	 * API：https://cloud.tencent.com/document/product/269/1624
	 * @param groupId 群组ID（必填）
	 * @param consumer 响应处理回调函数
	 */
	public void asyncDestroyGroup(String groupId, Consumer<GroupDestroyResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.build();
		this.asyncRequest(TimApiAddress.DESTROY_GROUP, requestBody, GroupDestroyResponse.class, consumer);
	}
	
	/**
	 * 21、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param userId 业务用户ID
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetJoinedGroupList(String userId, Consumer<GroupJoinedListGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Member_Account", this.getImUserByUserId(userId))
				.build();
		this.asyncRequest(TimApiAddress.GET_JOINED_GROUP_LIST, requestBody, GroupJoinedListGetResponse.class, consumer);
	}
	
	/**
	 * 21、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param userId 业务用户ID
	 * @param groupType 所拉取的群组所属的群组类型，例如 Public（陌生人社交群），Private（同新版本 Work，好友工作群）和 ChatRoom（同新版本 Meeting，会议群），如果指定 AVChatRoom（直播群），获得的成员可能不完整。
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetJoinedGroupList(String userId, String groupType, Consumer<GroupJoinedListGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("GroupType", groupType)
				.build();
		this.asyncRequest(TimApiAddress.GET_JOINED_GROUP_LIST, requestBody, GroupJoinedListGetResponse.class, consumer);
	}
	
	/**
	 * 21、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param userId 业务用户ID
	 * @param limit 一次最多获取多少个成员的资料，不得超过6000。如果不填，则获取群内全部成员的信息
	 * @param offset 从第几个成员开始获取，如果不填则默认为0，表示从第一个成员开始获取   
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetJoinedGroupList(String userId, Integer limit, Integer offset, Consumer<GroupJoinedListGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("Limit", limit)
				.put("Offset", offset)
				.build();
		this.asyncRequest(TimApiAddress.GET_JOINED_GROUP_LIST, requestBody, GroupJoinedListGetResponse.class, consumer);
	}
	
	/**
	 * 21、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param userId 业务用户ID
	 * @param groupType 所拉取的群组所属的群组类型，例如 Public（陌生人社交群），Private（同新版本 Work，好友工作群）和 ChatRoom（同新版本 Meeting，会议群），如果指定 AVChatRoom（直播群），获得的成员可能不完整。
	 * @param limit 一次最多获取多少个成员的资料，不得超过6000。如果不填，则获取群内全部成员的信息
	 * @param offset 从第几个成员开始获取，如果不填则默认为0，表示从第一个成员开始获取   
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetJoinedGroupList(String userId, String groupType, Integer limit, Integer offset, Consumer<GroupJoinedListGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("GroupType", groupType)
				.put("Limit", limit)
				.put("Offset", offset)
				.build();
		this.asyncRequest(TimApiAddress.GET_JOINED_GROUP_LIST, requestBody, GroupJoinedListGetResponse.class, consumer);
	}
	
	
	/**
	 * 22、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param userId 业务用户ID
	 * @param withHugeGroups 是否获取用户加入的 AVChatRoom(直播群)，0表示不获取，1表示获取。默认为0
	 * @param withNoActiveGroups 是否获取用户已加入但未激活的 Private（即新版本中 Work，好友工作群) 群信息，0表示不获取，1表示获取。默认为0
	 * @param limit 一次最多获取多少个成员的资料，不得超过6000。如果不填，则获取群内全部成员的信息
	 * @param offset 从第几个成员开始获取，如果不填则默认为0，表示从第一个成员开始获取   
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetJoinedGroupList(String userId, Integer withHugeGroups, Integer withNoActiveGroups, Integer limit, Integer offset, 
			Consumer<GroupJoinedListGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("WithHugeGroups", Objects.nonNull(withHugeGroups) ? withHugeGroups : 0)
				.put("WithNoActiveGroups", Objects.nonNull(withNoActiveGroups) ? withNoActiveGroups : 0)
				.put("Limit", limit)
				.put("Offset", offset)
				.build();
		this.asyncRequest(TimApiAddress.GET_JOINED_GROUP_LIST, requestBody, GroupJoinedListGetResponse.class, consumer);
	}
	
	/**
	 * 23、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param query 筛选条件
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetJoinedGroupList(GroupJoinedQuery query, Consumer<GroupJoinedListGetResponse> consumer) {
		this.asyncRequest(TimApiAddress.GET_JOINED_GROUP_LIST, query, GroupJoinedListGetResponse.class, consumer);
	}
	
	/**
	 * 24、查询用户在群组中的身份
	 * API：https://cloud.tencent.com/document/product/269/1626
	 * @param groupId 群组ID（必填）
	 * @param userIds 业务用户ID数组
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetRoleInGroup(String groupId, String[] userIds, Consumer<GroupMemberRoleGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("User_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.GET_ROLE_IN_GROUP, requestBody, GroupMemberRoleGetResponse.class, consumer);
	}

	/**
	 * 25、批量禁言和取消禁言
	 * API：https://cloud.tencent.com/document/product/269/1627
	 * @param groupId 群组ID（必填）
	 * @param userIds 需要禁言的用户帐号，最多支持500个帐号
	 * @param shutUpTime 需禁言时间，单位为秒，为0时表示取消禁言
	 * @param consumer 响应处理回调函数
	 */
	public void asyncUpdateGroupMemberShutUpTime(String groupId, String[] userIds, Integer shutUpTime, 
			Consumer<GroupMemberForbidSendMsgResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Members_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("ShutUpTime", Objects.nonNull(shutUpTime) ? shutUpTime : 0)
				.build();
		this.asyncRequest(TimApiAddress.FORBID_SEND_MSG, requestBody, GroupMemberForbidSendMsgResponse.class, consumer);
	}
	
	/**
	 * 26、获取被禁言群成员列表
	 * API：https://cloud.tencent.com/document/product/269/2925
	 * @param groupId 群组ID（必填）
	 * @param consumer 响应处理回调函数
	 */
	public void asyncGetGroup(String groupId, Consumer<GroupMemberShuttedUinResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.build();
		this.asyncRequest(TimApiAddress.GET_GROUP_SHUTTED_UIN, requestBody, GroupMemberShuttedUinResponse.class, consumer);
	}
	
	/**
	 * 27、在群组中发送普通消息
     * API：https://cloud.tencent.com/document/product/269/1629
     * @param groupId 群组ID（必填）
	 * @param random 随机数字，五分钟数字相同认为是重复消息
	 * @param offlinePushInfo 离线推送信息配置，详细可参阅 消息格式描述
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @param consumer 响应处理回调函数
	 */
	public void asyncSendGroupMsg(String groupId, String random, OfflinePushInfo offlinePushInfo, 
			MsgBody[] msgBody, Consumer<GroupMsgSendResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Random", random)
				.put("MsgBody", msgBody)
				.put("OfflinePushInfo", offlinePushInfo)
				.build();
		this.asyncRequest(TimApiAddress.SEND_GROUP_MSG, requestBody, GroupMsgSendResponse.class, consumer);
	}
	
	/**
	 * 28、在群组中发送普通消息
     * API：https://cloud.tencent.com/document/product/269/1629
     * @param groupId 群组ID（必填）
	 * @param random 随机数字，五分钟数字相同认为是重复消息
	 * @param forbidCallbackControl 消息回调禁止开关，只对单条消息有效，ForbidBeforeSendMsgCallback 表示禁止发消息前回调，ForbidAfterSendMsgCallback 表示禁止发消息后回调
	 * @param offlinePushInfo 离线推送信息配置，详细可参阅 消息格式描述
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @param consumer 响应处理回调函数
	 */
	public void asyncSendGroupMsg(String groupId, String random, String[] forbidCallbackControl, OfflinePushInfo offlinePushInfo, 
			MsgBody[] msgBody, Consumer<GroupMsgSendResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Random", random)
				.put("ForbidCallbackControl", forbidCallbackControl)
				.put("OfflinePushInfo", offlinePushInfo)
				.put("MsgBody", msgBody)
				.build();
		this.asyncRequest(TimApiAddress.SEND_GROUP_MSG, requestBody, GroupMsgSendResponse.class, consumer);
	}
	
	/**
	 * 29、在群组中发送普通消息-指定消息不存离线及漫游
	 * 
     * API：https://cloud.tencent.com/document/product/269/1629
     * @param groupId 群组ID（必填）
	 * @param random 随机数字，五分钟数字相同认为是重复消息
	 * @param onlineOnlyFlag 如果消息体中指定 OnlineOnlyFlag，只要值大于0，则消息表示只在线下发，不存离线和漫游（AVChatRoom 和 BChatRoom 不允许使用）。
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @param consumer 响应处理回调函数
	 */
	public void asyncSendGroupMsg(String groupId, String random, String onlineOnlyFlag, MsgBody[] msgBody, Consumer<GroupMsgSendResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Random", random)
				.put("OnlineOnlyFlag", onlineOnlyFlag) 
				.put("MsgBody", msgBody)
				.build();
		this.asyncRequest(TimApiAddress.SEND_GROUP_MSG, requestBody, GroupMsgSendResponse.class, consumer);
	}

	/**
	 * 30、在群组中发送普通消息-指定消息不存离线及漫游
	 * 
     * API：https://cloud.tencent.com/document/product/269/1629
     * @param groupId 群组ID（必填）
	 * @param random 随机数字，五分钟数字相同认为是重复消息
	 * @param onlineOnlyFlag 如果消息体中指定 OnlineOnlyFlag，只要值大于0，则消息表示只在线下发，不存离线和漫游（AVChatRoom 和 BChatRoom 不允许使用）
	 * @param forbidCallbackControl 消息回调禁止开关，只对单条消息有效，ForbidBeforeSendMsgCallback 表示禁止发消息前回调，ForbidAfterSendMsgCallback 表示禁止发消息后回调
	 * @param offlinePushInfo 离线推送信息配置，详细可参阅 消息格式描述
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @param consumer 响应处理回调函数
	 */
	public void asyncSendGroupMsg(String groupId, String random, String onlineOnlyFlag, String[] forbidCallbackControl, OfflinePushInfo offlinePushInfo, 
			MsgBody[] msgBody, Consumer<GroupMsgSendResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Random", random)
				.put("OnlineOnlyFlag", onlineOnlyFlag) 
				.put("ForbidCallbackControl", forbidCallbackControl)
				.put("OfflinePushInfo", offlinePushInfo)
				.put("MsgBody", msgBody)
				.build();
		this.asyncRequest(TimApiAddress.SEND_GROUP_MSG, requestBody, GroupMsgSendResponse.class, consumer);
	}
	
	/**
	 * 31、在群组中发送普通消息-指定消息发送者
	 * 可以指定某一位群成员作为消息发送者，在 From_Account 中设置。其他用户收到群消息后，显示的消息来自 App 管理员指定的那一位群成员。
     * API：https://cloud.tencent.com/document/product/269/1629
     * @param groupId 群组ID（必填）
     * @param userId 消息来源帐号ID。如果不填写该字段，则默认消息的发送者为调用该接口时使用的 App 管理员帐号。除此之外，App 亦可通过该字段“伪造”消息的发送者，从而实现一些特殊的功能需求。需要注意的是，如果指定该字段，必须要确保字段中的帐号是存在的
	 * @param random 随机数字，五分钟数字相同认为是重复消息
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @param consumer 响应处理回调函数
	 */
	public void asyncSendGroupProxyMsg(String groupId, String userId, String random, MsgBody[] msgBody, Consumer<GroupMsgSendResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("From_Account", this.getImUserByUserId(userId))
				.put("Random", random)
				.put("MsgBody", msgBody)
				.build();
		this.asyncRequest(TimApiAddress.SEND_GROUP_MSG, requestBody, GroupMsgSendResponse.class, consumer);
	}
	
	/**
	 * 32、在群组中发送普通消息
	 * API：https://cloud.tencent.com/document/product/269/1629
	 * @param message 消息对象
	 * @param consumer 响应处理回调函数
	 */
	public void asyncSendGroupMsg(GroupMessage message, Consumer<GroupMsgSendResponse> consumer) {
		this.asyncRequest(TimApiAddress.SEND_GROUP_MSG, message, GroupMsgSendResponse.class, consumer);
	}
	
	/**
	 * 33、在群组中发送系统通知
     * API：https://cloud.tencent.com/document/product/269/1630
     * @param groupId 群组ID（必填）
	 * @param content 系统通知的内容
	 * @param consumer 响应处理回调函数
	 */
	public void asyncSendGroupSysMsg(String groupId, String content, Consumer<GroupSystemNotificationSendResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Content", content)
				.build();
		this.asyncRequest(TimApiAddress.SEND_GROUP_SYSTEM_NOTIFICATION, requestBody, GroupSystemNotificationSendResponse.class, consumer);
	}
	
	/**
	 * 34、在群组中发送系统通知
     * API：https://cloud.tencent.com/document/product/269/1630
     * @param groupId 群组ID（必填）
	 * @param userIds 接收者群成员列表，请填写接收者 UserID，不填或为空表示全员下发
	 * @param content 系统通知的内容
	 * @param consumer 响应处理回调函数
	 */
	public void asyncSendGroupSysMsg(String groupId, String[] userIds, String content, Consumer<GroupSystemNotificationSendResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("ToMembers_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("Content", content)
				.build();
		this.asyncRequest(TimApiAddress.SEND_GROUP_SYSTEM_NOTIFICATION, requestBody, GroupSystemNotificationSendResponse.class, consumer);
	}
	
	/**
	 * 32、转让群主
     * API：https://cloud.tencent.com/document/product/269/1633
     * @param groupId 要被转移的群 ID（必填）
     * @param userId 新群主 ID（必填）
	 * @param consumer 响应处理回调函数
	 */
	public void asyncChangeGroupOwner(String groupId, String userId, Consumer<GroupChangeOwnerResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("NewOwner_Account", this.getImUserByUserId(userId))
				.build();
		this.asyncRequest(TimApiAddress.CHANGE_GROUP_OWNER, requestBody, GroupChangeOwnerResponse.class, consumer);
	}
	
	/**
	 * 33、撤回群消息
     * API：https://cloud.tencent.com/document/product/269/12341
     * @param groupId 要被转移的群 ID（必填）
     * @param msgSeqs 被撤回的消息 seq 数组，一次请求最多可以撤回10条消息 seq
	 * @param consumer 响应处理回调函数
	 */
	public void asyncRecallGroupMsg(String groupId, Integer[] msgSeqs, Consumer<GroupMsgRecallResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("MsgSeqList", Stream.of(msgSeqs).map(msgSeq -> {
					Map<String, Object> msgSeqMap = new HashMap<>();
					msgSeqMap.put("MsgSeq", msgSeq);
					return msgSeqMap;
				}).collect(Collectors.toList()))
				.build();
		this.asyncRequest(TimApiAddress.GROUP_MSG_RECALL, requestBody, GroupMsgRecallResponse.class, consumer);
	}

	/**
	 * 34、导入群基础资料
	 * App 管理员可以通过该接口导入群组，不会触发回调、不会下发通知；当 App 需要从其他即时通信系统迁移到即时通信 IM 时，使用该协议导入存量群组数据。
	 * API：https://cloud.tencent.com/document/product/269/1634
	 * @param userId 需要修改基础信息的群组的 ID
	 * @param groupType 群组类型，包括 Public（陌生人社交群），Private（同新版本中的Work，好友工作群）， ChatRoom（同新版本中的Meeting，会议群）
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param createTime  群组的创建时间（选填，不填会以请求时刻为准）
	 * @param consumer 响应处理回调函数
	 */
	public void asyncImportGroup(String userId, String groupType, String groupName, Integer createTime, Consumer<GroupMsgImportResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Owner_Account", this.getImUserByUserId(userId))
				.put("Type", groupType)
				.put("Name", groupName)
				.put("CreateTime", createTime)
				.build();
		this.asyncRequest(TimApiAddress.IMPORT_GROUP, requestBody, GroupMsgImportResponse.class, consumer);
	}
	
	/**
	 * 35、导入群基础资料
	 * App 管理员可以通过该接口导入群组，不会触发回调、不会下发通知；当 App 需要从其他即时通信系统迁移到即时通信 IM 时，使用该协议导入存量群组数据。
	 * API：https://cloud.tencent.com/document/product/269/1634
	 * @param userId 需要修改基础信息的群组的 ID
	 * @param groupId 为了使得群组 ID 更加简单，便于记忆传播，腾讯云支持 App 在通过 REST API 创建群组时自定义群组 ID。详细请参阅 群组系统
	 * @param groupType 群组类型，包括 Public（陌生人社交群），Private（同新版本中的Work，好友工作群）， ChatRoom（同新版本中的Meeting，会议群）
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param introduction 群简介，最长240字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param notification  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param faceUrl  群头像 URL，最长100字节
	 * @param maxMemberCount  最大群成员数量，缺省时的默认值：私有群是200，公开群是2000，聊天室是6000，音视频聊天室和在线成员广播大群无限制
	 * @param applyJoinOption  申请加群处理方式。包含 FreeAccess（自由加入），NeedPermission（需要验证），DisableApply（禁止加群），不填默认为 NeedPermission（需要验证）仅当创建支持申请加群的 群组 时，该字段有效
	 * @param appDefinedData  群组维度的自定义字段，默认情况是没有的，需要开通，详细请参阅 群组系统
	 * @param createTime  群组的创建时间（选填，不填会以请求时刻为准）
	 * @param consumer 响应处理回调函数
	 */
	public void asyncImportGroup(String userId, String groupId, String groupType, String groupName,
			String introduction, String notification, String faceUrl, Integer maxMemberCount, String applyJoinOption,
			List<MapKV> appDefinedData, Integer createTime, Consumer<GroupMsgImportResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Owner_Account", this.getImUserByUserId(userId))
				.put("Name", groupName)
				.put("Introduction", introduction)
				.put("Notification", notification)
				.put("FaceUrl", faceUrl)
				.put("MaxMemberCount", maxMemberCount)
				.put("ApplyJoinOption", applyJoinOption)
				.put("AppDefinedData", appDefinedData)
				.build();
		this.asyncRequest(TimApiAddress.IMPORT_GROUP, requestBody, GroupMsgImportResponse.class, consumer);
	}

	/**
	 * 36、导入群消息
	 * API：https://cloud.tencent.com/document/product/269/1635
	 * @param groupId 要导入消息的群 ID
	 * @param msgList 导入的消息列表
	 * @param consumer 响应处理回调函数
	 */
	public void asyncImportGroupMsg(String groupId, GroupMessageImport[] msgList, Consumer<GroupMsgImportResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("MsgList", msgList)
				.build();
		this.asyncRequest(TimApiAddress.IMPORT_GROUP_MSG, requestBody, GroupMsgImportResponse.class, consumer);
	}
	
	/**
	 * 37、导入群成员
	 * API：https://cloud.tencent.com/document/product/269/1636
	 * @param groupId 要导入消息的群 ID
	 * @param memberList 导入的消息列表
	 * @param consumer 响应处理回调函数
	 */
	public void asyncImportGroupMember(String groupId, GroupMemberImport[] memberList, Consumer<GroupMemberImportResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("MemberList", memberList)
				.build();
		this.asyncRequest(TimApiAddress.IMPORT_GROUP_MSG, requestBody, GroupMemberImportResponse.class, consumer);
	}
	
	/**
	 * 38、设置成员未读消息计数
	 * API：https://cloud.tencent.com/document/product/269/1637
	 * @param groupId 操作的群 ID
	 * @param userId 要操作的群成员 ID
	 * @param unreadMsgNum 成员未读消息数
	 * @param consumer 响应处理回调函数
	 */
	public void asyncSetGroupMemberUnreadMsgNum(String groupId, String userId, Integer unreadMsgNum,
			Consumer<GroupMsgUnreadNumResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("UnreadMsgNum", unreadMsgNum)
				.build();
		this.asyncRequest(TimApiAddress.SET_UNREAD_MSG_NUM, requestBody, GroupMsgUnreadNumResponse.class, consumer);
	}

	/**
	 * 39、撤回指定用户发送的消息
	 * API：https://cloud.tencent.com/document/product/269/2359
	 * @param groupId 要撤回消息的群 ID
	 * @param userId 被撤回消息的发送者 ID
	 * @param consumer 响应处理回调函数
	 */ 
	public void asyncRecallGroupMemberMsg(String groupId, String userId, Consumer<GroupMsgUnreadNumResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Sender_Account", this.getImUserByUserId(userId))
				.build();
		this.asyncRequest(TimApiAddress.DELETE_GROUP_MSG_BY_SENDER, requestBody, GroupMsgUnreadNumResponse.class, consumer);
	}
	
	/**
	 * 40、拉取群历史消息
	 * API：https://cloud.tencent.com/document/product/269/2738
	 * @param groupId 要拉取历史消息的群组 ID
	 * @param reqMsgSeq 拉取消息的最大 seq
	 * @param consumer 响应处理回调函数
	 */ 
	public void asyncGetGroupMsg(String groupId, Integer reqMsgSeq, Consumer<GroupMsgGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("ReqMsgSeq", reqMsgSeq)
				.put("ReqMsgNumber", 20)
				.build();
		this.asyncRequest(TimApiAddress.GROUP_MSG_GET_SIMPLE, requestBody, GroupMsgGetResponse.class, consumer);
	}
	
	/**
	 * 41、拉取群历史消息
	 * API：https://cloud.tencent.com/document/product/269/2738
	 * @param groupId 要拉取历史消息的群组 ID
	 * @param reqMsgSeq 拉取消息的最大 seq
	 * @param reqMsgNumber 拉取的历史消息的条数，目前一次请求最多返回20条历史消息，所以这里最好小于等于20
	 * @param consumer 响应处理回调函数
	 */ 
	public void asyncGetGroupMsg(String groupId, Integer reqMsgSeq, Integer reqMsgNumber, Consumer<GroupMsgGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("ReqMsgSeq", reqMsgSeq)
				.put("ReqMsgNumber", reqMsgNumber)
				.build();
		this.asyncRequest(TimApiAddress.GROUP_MSG_GET_SIMPLE, requestBody, GroupMsgGetResponse.class, consumer);
	}
	
	/**
	 * 42、获取直播群在线人数：App 管理员可以根据群组 ID 获取直播群在线人数。
	 * API：https://cloud.tencent.com/document/product/269/49180
	 * @param groupId 要撤回消息的群 ID
	 * @param consumer 响应处理回调函数
	 */ 
	public void asyncGetGroupMemberOnlineNum(String groupId, Consumer<GroupMemberOnlineNumGetResponse> consumer) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.build();
		this.asyncRequest(TimApiAddress.GET_ONLINE_MEMBER_NUM, requestBody, GroupMemberOnlineNumGetResponse.class, consumer);
	}
}
