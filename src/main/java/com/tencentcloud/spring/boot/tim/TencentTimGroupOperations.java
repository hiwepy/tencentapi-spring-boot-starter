package com.tencentcloud.spring.boot.tim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

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
import com.tencentcloud.spring.boot.tim.req.group.GroupType;
import com.tencentcloud.spring.boot.tim.req.message.MsgBody;
import com.tencentcloud.spring.boot.tim.req.message.OfflinePushInfo;
import com.tencentcloud.spring.boot.tim.resp.group.AppGroupGetResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupChangeOwnerResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupCreateResponse;
import com.tencentcloud.spring.boot.tim.resp.group.GroupDestoryResponse;
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

/**
 * 6、群组管理
 * https://cloud.tencent.com/document/product/269/1614
 */
public class TencentTimGroupOperations extends TencentTimOperations {

	public TencentTimGroupOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}

	/**
	 * 1、获取 App 中的所有群组
	 * API：https://cloud.tencent.com/document/product/269/1614
	 * @return 操作结果
	 */
	public AppGroupGetResponse getAppGroupList() {
		Map<String, Object> requestBody = ImmutableMap.of();
		return super.request(TimApiAddress.GET_APPID_GROUP_LIST, requestBody, AppGroupGetResponse.class);
	}

	/**
	 * 2、获取 App 中的所有群组
	 * API：https://cloud.tencent.com/document/product/269/1614
	 * @param limit 本次获取的群组 ID 数量的上限，不得超过 10000。如果不填，默认为最大值 10000
	 * @param next  群太多时分页拉取标志，第一次填0，以后填上一次返回的值，返回的 Next 为0代表拉完了
	 * @return 操作结果
	 */
	public AppGroupGetResponse getAppGroupList(Integer limit, Integer next) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Limit", limit)
				.put("Next", next)
				.build();
		return super.request(TimApiAddress.GET_APPID_GROUP_LIST, requestBody, AppGroupGetResponse.class);
	}

	/**
	 * 3、获取 App 中的所有群组
	 * API：https://cloud.tencent.com/document/product/269/1614
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @return 操作结果
	 */
	public AppGroupGetResponse getAppGroupList(GroupType groupType) {
		Map<String, Object> requestBody = ImmutableMap.of("GroupType", groupType.getValue());
		return super.request(TimApiAddress.GET_APPID_GROUP_LIST, requestBody, AppGroupGetResponse.class);
	}

	/**
	 * 4、获取 App 中的所有群组
	 * API：https://cloud.tencent.com/document/product/269/1614
	 * @param limit 本次获取的群组 ID 数量的上限，不得超过 10000。如果不填，默认为最大值 10000
	 * @param next  群太多时分页拉取标志，第一次填0，以后填上一次返回的值，返回的 Next 为0代表拉完了
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @return 操作结果
	 */
	public AppGroupGetResponse getAppGroupList(Integer limit, Integer next, GroupType groupType) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Limit", limit)
				.put("Next", next)
				.put("GroupType", groupType.getValue())
				.build();
		return super.request(TimApiAddress.GET_APPID_GROUP_LIST, requestBody, AppGroupGetResponse.class);
	}

	/**
	 * 5、创建群组
	 * API：https://cloud.tencent.com/document/product/269/1615
	 * @param ownerId 业务用户ID
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @return 操作结果
	 */
	public GroupCreateResponse createGroup(String ownerId, String groupType, String groupName) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Owner_Account", this.getImUserByUserId(ownerId))
				.put("Type", groupType)
				.put("Name", groupName)
				.build();
		return super.request(TimApiAddress.CREATE_GROUP, requestBody, GroupCreateResponse.class);
	}

	/**
	 * 6、创建群组，并指定群简介、群公告等群基础信息
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
	 * @return 操作结果
	 */
	public GroupCreateResponse createGroup(String ownerId, String groupType, String groupName, String introduction,
			String notification, String faceUrl, Integer maxMemberCount, String applyJoinOption) {
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
		return super.request(TimApiAddress.CREATE_GROUP, requestBody, GroupCreateResponse.class);
	}

	/**
	 * 7、创建群组，并指定初始化群成员列表，群成员列表在请求包说明表中有描述。
	 * API：https://cloud.tencent.com/document/product/269/1615
	 * @param ownerId 业务用户ID
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param groupMembers  初始群成员列表，最多500个（选填）
	 * @return 操作结果
	 */
	public GroupCreateResponse createGroupWithMembers(String ownerId, String groupType, String groupName, GroupMember ... groupMembers) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Owner_Account", this.getImUserByUserId(ownerId))
				.put("Type", groupType)
				.put("Name", groupName)
				.put("MemberList", groupMembers)
				.build();
		return super.request(TimApiAddress.CREATE_GROUP, requestBody, GroupCreateResponse.class);
	}

	/**
	 * 8、创建群组，并指定自定义群组 ID
	 * API：https://cloud.tencent.com/document/product/269/1615
	 * @param ownerId 业务用户ID
	 * @param groupId 用户自定义群组ID
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param groupMembers  初始群成员列表，最多500个（选填）
	 * @return 操作结果
	 */
	public GroupCreateResponse createGroupWithMembers(String ownerId, String groupId, String groupType, String groupName, GroupMember ... groupMembers) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Owner_Account", this.getImUserByUserId(ownerId))
				.put("Type", groupType)
				.put("GroupId", groupId)
				.put("Name", groupName)
				.put("MemberList", groupMembers)
				.build();
		return super.request(TimApiAddress.CREATE_GROUP, requestBody, GroupCreateResponse.class);
	}

	/**
	 * 9、创建群组
	 * API：https://cloud.tencent.com/document/product/269/1615
	 * @param group 群组信息
	 * @return 操作结果
	 */
	public GroupCreateResponse createGroup(GroupInfo group) {
		return super.request(TimApiAddress.CREATE_GROUP, group, GroupCreateResponse.class);
	}

	/**
	 * 10、修改群基础资料
	 * API：https://cloud.tencent.com/document/product/269/1620
	 * @param groupId 需要修改基础信息的群组的 ID
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param introduction 群简介，最长240字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param notification  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param faceUrl  群头像 URL，最长100字节
	 * @param maxMemberCount  最大群成员数量，缺省时的默认值：私有群是200，公开群是2000，聊天室是6000，音视频聊天室和在线成员广播大群无限制
	 * @param applyJoinOption  申请加群处理方式。包含 FreeAccess（自由加入），NeedPermission（需要验证），DisableApply（禁止加群），不填默认为 NeedPermission（需要验证）仅当创建支持申请加群的 群组 时，该字段有效
	 * @return 操作结果
	 */
	public GroupModifyResponse updateGroup(String groupId, String groupName, String introduction,
			String notification, String faceUrl, Integer maxMemberCount, String applyJoinOption) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Name", groupName)
				.put("Introduction", introduction)
				.put("Notification", notification)
				.put("FaceUrl", faceUrl)
				.put("MaxMemberCount", maxMemberCount)
				.put("ApplyJoinOption", applyJoinOption)
				.build();
		return super.request(TimApiAddress.MODIFY_GROUP_BASE_INFO, requestBody, GroupModifyResponse.class);
	}

	/**
	 * 11、修改群基础资料
	 * API：https://cloud.tencent.com/document/product/269/1620
	 * @param group 修改内容
	 * @return 操作结果
	 */
	public GroupModifyResponse updateGroup(GroupModify group) {
		return super.request(TimApiAddress.MODIFY_GROUP_BASE_INFO, group, GroupModifyResponse.class);
	}

	/**
	 * 12、获取群详细资料
	 * API：https://cloud.tencent.com/document/product/269/1616
	 * @param groupIds 群组列表（必填）
	 * @return 操作结果
	 */
	public GroupInfoGetResponse getGroupInfo(String... groupIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupIdList", groupIds)
				.build();
		return super.request(TimApiAddress.GET_GROUP_INFO, requestBody, GroupInfoGetResponse.class);
	}

	/**
	 * 13、获取群详细资料
	 * API：https://cloud.tencent.com/document/product/269/1616
	 * @param groupIds 群组列表（必填）
	 * @param responseFilter 包含三个过滤器：GroupBaseInfoFilter，MemberInfoFilter，AppDefinedDataFilter_Group，分别是基础信息字段过滤器，成员信息字段过滤器，群组维度的自定义字段过滤器
	 * @return 操作结果
	 */
	public GroupInfoGetResponse getGroupInfo(String[] groupIds, GroupResponseFilter responseFilter) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupIdList", groupIds)
				.put("ResponseFilter", responseFilter)
				.build();
		return super.request(TimApiAddress.GET_GROUP_INFO, requestBody, GroupInfoGetResponse.class);
	}

	/**
	 * 14、添加群成员
	 * API：https://cloud.tencent.com/document/product/269/1617
	 * @param groupId 群组ID（必填）
	 * @param silence 是否静默加人。0：非静默加人；1：静默加人。不填该字段默认为0
	 * @param userIds 待添加的群成员
	 * @return 操作结果
	 */
	public GroupMemberAddResponse addGroupMember(String groupId, Integer silence, String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Silence", Objects.nonNull(silence) ? silence : 0)
				.put("MemberList", Stream.of(userIds).map(uid -> {
					return new GroupMemberAccount(this.getImUserByUserId(uid));
				}).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.ADD_GROUP_MEMBER, requestBody, GroupMemberAddResponse.class);
	}

	/**
	 * 15、删除群成员
	 * API：https://cloud.tencent.com/document/product/269/1622
	 * @param groupId 群组ID（必填）
	 * @param reason 踢出用户原因
	 * @param userIds 待添加的群成员数组
	 * @return 操作结果
	 */
	public GroupMemberDeleteResponse deleteGroupMember(String groupId, String reason, String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Reason", reason)
				.put("MemberToDel_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.DELETE_GROUP_MEMBER, requestBody, GroupMemberDeleteResponse.class);
	}

	/**
	 * 16、删除群成员
	 * API：https://cloud.tencent.com/document/product/269/1622
	 * @param groupId 群组ID（必填）
	 * @param silence 是否静默删人。0表示非静默删人，1表示静默删人。静默即删除成员时不通知群里所有成员，只通知被删除群成员。不填写该字段时默认为0
	 * @param reason 踢出用户原因
	 * @param userIds 待添加的群成员数组
	 * @return 操作结果
	 */
	public GroupMemberDeleteResponse deleteGroupMember(String groupId, Integer silence, String reason,  String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Silence", Objects.nonNull(silence) ? silence : 0)
				.put("Reason", reason)
				.put("MemberToDel_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.DELETE_GROUP_MEMBER, requestBody, GroupMemberDeleteResponse.class);
	}

	/**
	 * 17、修改群成员资料-设置/取消指定群成员的管理员身份
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param groupId 群组ID（必填）
	 * @param userId 要操作的群成员ID（必填）
	 * @param role 成员身份，Admin/Member 分别为设置/取消管理员
	 * @return 操作结果
	 */
	public GroupMemberModifyResponse updateGroupMemberRole(String groupId, String userId, String role) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("Role", role)
				.build();
		return super.request(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, requestBody, GroupMemberModifyResponse.class);
	}

	/**
	 * 18、修改群成员资料-设置成员消息屏蔽位
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param groupId 群组ID（必填）
	 * @param userId 要操作的群成员ID（必填）
	 * @param msgFlag 消息屏蔽类型： AcceptAndNotify 代表接收并提示消息，Discard 代表不接收也不提示消息，AcceptNotNotify 代表接收消息但不提示。
	 * @return 操作结果
	 */
	public GroupMemberModifyResponse updateGroupMemberMsgFlag(String groupId, String userId, String msgFlag) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("MsgFlag", msgFlag)
				.build();
		return super.request(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, requestBody, GroupMemberModifyResponse.class);
	}

	/**
	 * 19、修改群成员资料-设置成员的群名片
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param groupId 群组ID（必填）
	 * @param userId 要操作的群成员ID（必填）
	 * @param nameCard 群名片（最大不超过50个字节）
	 * @return 操作结果
	 */
	public GroupMemberModifyResponse updateGroupMemberNameCard(String groupId, String userId, String nameCard) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("NameCard", nameCard)
				.build();
		return super.request(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, requestBody, GroupMemberModifyResponse.class);
	}

	/**
	 * 20、修改群成员资料-设置成员自定义字段
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param groupId 群组ID（必填）
	 * @param userId 要操作的群成员ID（必填）
	 * @param appMemberDefinedData 群成员维度的自定义字段，默认情况是没有的，可以通过 即时通信 IM 控制台 进行配置，详情请参阅 群组系统
	 * @return 操作结果
	 */
	public GroupMemberModifyResponse updateGroupMemberAttrs(String groupId, String userId, List<MapKV> appMemberDefinedData) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("AppMemberDefinedData", appMemberDefinedData)
				.build();
		return super.request(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, requestBody, GroupMemberModifyResponse.class);
	}

	/**
	 * 21、修改群成员资料-设置群成员禁言时间
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param groupId 群组ID（必填）
	 * @param userId 要操作的群成员ID（必填）
	 * @param shutUpTime 群成员的禁言时间，单位为秒，0表示取消禁言
	 * @return 操作结果
	 */
	public GroupMemberModifyResponse updateGroupMemberShutUpTime(String groupId, String userId, Integer shutUpTime) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("ShutUpTime", Objects.nonNull(shutUpTime) ? shutUpTime : 0)
				.build();
		return super.request(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, requestBody, GroupMemberModifyResponse.class);
	}

	/**
	 * 22、修改群成员资料
	 * API：https://cloud.tencent.com/document/product/269/1623
	 * @param member 群成员资料
	 * @return 操作结果
	 */
	public GroupMemberModifyResponse updateGroupMember(GroupMemberModify member) {
		return super.request(TimApiAddress.MODIFY_GROUP_MEMBER_INFO, member, GroupMemberModifyResponse.class);
	}

	/**
	 * 23、获取群成员详细资料
	 * API：https://cloud.tencent.com/document/product/269/1617
	 * @param groupId 群组ID（必填）
	 * @return 操作结果
	 */
	public GroupMemberGetResponse getGroupMembers(String groupId) {
		return this.getGroupMembers(groupId, true);
	}

	/**
	 * 24、获取群成员详细资料
	 * API：https://cloud.tencent.com/document/product/269/1617
	 * @param groupId 群组ID（必填）
	 * @param filterNotFound 是否过滤IM账号不存在的用户（群成员IM账号不存在的时，memberAccount值为@TLS#NOT_FOUND）
	 * @return 操作结果
	 */
	public GroupMemberGetResponse getGroupMembers(String groupId, boolean filterNotFound) {
		Map<String, Object> requestBody = ImmutableMap.of("GroupId", groupId);
		GroupMemberGetResponse response = super.request(TimApiAddress.GET_GROUP_MEMBER_INFO, requestBody, GroupMemberGetResponse.class);
		if(filterNotFound && response.isSuccess() && response.getMemberNum() > 0) {
			List<GroupMember> memberList = response.getMemberList().stream()
				.filter(member -> !StringUtils.startsWith(member.getMemberAccount(), "@TLS#NOT_FOUND"))
				.collect(Collectors.toList());
			response.setMemberList(memberList);
		}
		return response;
	}

	/**
	 * 25、获取群成员详细资料
	 * API：https://cloud.tencent.com/document/product/269/1617
	 * @param groupId 群组ID（必填）
	 * @param limit 一次最多获取多少个成员的资料，不得超过6000。如果不填，则获取群内全部成员的信息
	 * @param offset 从第几个成员开始获取，如果不填则默认为0，表示从第一个成员开始获取
	 * @return 操作结果
	 */
	public GroupMemberGetResponse getGroupMembers(String groupId, Integer limit, Integer offset) {
		return this.getGroupMembers(groupId, limit, offset, true);
	}

	/**
	 * 27、获取群成员详细资料
	 * API：https://cloud.tencent.com/document/product/269/1617
	 * @param groupId 群组ID（必填）
	 * @param limit 一次最多获取多少个成员的资料，不得超过6000。如果不填，则获取群内全部成员的信息
	 * @param offset 从第几个成员开始获取，如果不填则默认为0，表示从第一个成员开始获取
	 * @param filterNotFound 是否过滤IM账号不存在的用户（群成员IM账号不存在的时，memberAccount值为@TLS#NOT_FOUND）
	 * @return 操作结果
	 */
	public GroupMemberGetResponse getGroupMembers(String groupId, Integer limit, Integer offset, boolean filterNotFound) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Limit", limit)
				.put("Offset", offset)
				.build();
		GroupMemberGetResponse response = super.request(TimApiAddress.GET_GROUP_MEMBER_INFO, requestBody, GroupMemberGetResponse.class);
		if(filterNotFound && response.isSuccess() && response.getMemberNum() > 0) {
			List<GroupMember> memberList = response.getMemberList().stream()
				.filter(member -> !StringUtils.startsWith(member.getMemberAccount(), "@TLS#NOT_FOUND"))
				.collect(Collectors.toList());
			response.setMemberList(memberList);
		}
		return response;
	}

	/**
	 * 28、获取群成员详细资料
	 * API：https://cloud.tencent.com/document/product/269/1617
	 * @param query 筛选条件
	 * @return 操作结果
	 */
	public GroupMemberGetResponse getGroupMembers(GroupMemberQuery query) {
		return super.request(TimApiAddress.GET_GROUP_MEMBER_INFO, query, GroupMemberGetResponse.class);
	}

	/**
	 * 29、解散群组（群组解散之后将无法恢复，请谨慎调用该接口）
	 * API：https://cloud.tencent.com/document/product/269/1624
	 * @param groupId 群组ID（必填）
	 * @return 操作结果
	 */
	public GroupDestoryResponse destoryGroup(String groupId) {
		Map<String, Object> requestBody = ImmutableMap.of("GroupId", groupId);
		return super.request(TimApiAddress.DESTROY_GROUP, requestBody, GroupDestoryResponse.class);
	}

	/**
	 * 30、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param userId 业务用户ID
	 * @return 操作结果
	 */
	public GroupJoinedListGetResponse getJoinedGroupList(String userId) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Member_Account", this.getImUserByUserId(userId))
				.build();
		return super.request(TimApiAddress.GET_JOINED_GROUP_LIST, requestBody, GroupJoinedListGetResponse.class);
	}

	/**
	 * 31、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param userId 业务用户ID
	 * @param groupType 所拉取的群组所属的群组类型，例如 Public（陌生人社交群），Private（同新版本 Work，好友工作群）和 ChatRoom（同新版本 Meeting，会议群），如果指定 AVChatRoom（直播群），获得的成员可能不完整。
	 * @return 操作结果
	 */
	public GroupJoinedListGetResponse getJoinedGroupList(String userId, String groupType) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("GroupType", groupType)
				.build();
		return super.request(TimApiAddress.GET_JOINED_GROUP_LIST, requestBody, GroupJoinedListGetResponse.class);
	}

	/**
	 * 32、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param userId 业务用户ID
	 * @param limit 一次最多获取多少个成员的资料，不得超过6000。如果不填，则获取群内全部成员的信息
	 * @param offset 从第几个成员开始获取，如果不填则默认为0，表示从第一个成员开始获取
	 * @return 操作结果
	 */
	public GroupJoinedListGetResponse getJoinedGroupList(String userId, Integer limit, Integer offset) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("Limit", limit)
				.put("Offset", offset)
				.build();
		return super.request(TimApiAddress.GET_JOINED_GROUP_LIST, requestBody, GroupJoinedListGetResponse.class);
	}

	/**
	 * 33、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param userId 业务用户ID
	 * @param groupType 所拉取的群组所属的群组类型，例如 Public（陌生人社交群），Private（同新版本 Work，好友工作群）和 ChatRoom（同新版本 Meeting，会议群），如果指定 AVChatRoom（直播群），获得的成员可能不完整。
	 * @param limit 一次最多获取多少个成员的资料，不得超过6000。如果不填，则获取群内全部成员的信息
	 * @param offset 从第几个成员开始获取，如果不填则默认为0，表示从第一个成员开始获取
	 * @return 操作结果
	 */
	public GroupJoinedListGetResponse getJoinedGroupList(String userId, String groupType, Integer limit, Integer offset) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("GroupType", groupType)
				.put("Limit", limit)
				.put("Offset", offset)
				.build();
		return super.request(TimApiAddress.GET_JOINED_GROUP_LIST, requestBody, GroupJoinedListGetResponse.class);
	}


	/**
	 * 34、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param userId 业务用户ID
	 * @param withHugeGroups 是否获取用户加入的 AVChatRoom(直播群)，0表示不获取，1表示获取。默认为0
	 * @param withNoActiveGroups 是否获取用户已加入但未激活的 Private（即新版本中 Work，好友工作群) 群信息，0表示不获取，1表示获取。默认为0
	 * @param limit 一次最多获取多少个成员的资料，不得超过6000。如果不填，则获取群内全部成员的信息
	 * @param offset 从第几个成员开始获取，如果不填则默认为0，表示从第一个成员开始获取
	 * @return 操作结果
	 */
	public GroupJoinedListGetResponse getJoinedGroupList(String userId, Integer withHugeGroups, Integer withNoActiveGroups, Integer limit, Integer offset) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("WithHugeGroups", Objects.nonNull(withHugeGroups) ? withHugeGroups : 0)
				.put("WithNoActiveGroups", Objects.nonNull(withNoActiveGroups) ? withNoActiveGroups : 0)
				.put("Limit", limit)
				.put("Offset", offset)
				.build();
		return super.request(TimApiAddress.GET_JOINED_GROUP_LIST, requestBody, GroupJoinedListGetResponse.class);
	}

	/**
	 * 35、获取用户所加入的群组
	 * API：https://cloud.tencent.com/document/product/269/1625
	 * @param query 筛选条件
	 * @return 操作结果
	 */
	public GroupJoinedListGetResponse getJoinedGroupList(GroupJoinedQuery query) {
		return super.request(TimApiAddress.GET_JOINED_GROUP_LIST, query, GroupJoinedListGetResponse.class);
	}

	/**
	 * 36、查询用户在群组中的身份
	 * API：https://cloud.tencent.com/document/product/269/1626
	 * @param groupId 群组ID（必填）
	 * @param userIds 业务用户ID数组
	 * @return 操作结果
	 */
	public GroupMemberRoleGetResponse getRoleInGroup(String groupId, String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("User_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.GET_ROLE_IN_GROUP, requestBody, GroupMemberRoleGetResponse.class);
	}

	/**
	 * 37、批量禁言和取消禁言
	 * API：https://cloud.tencent.com/document/product/269/1627
	 * @param groupId 群组ID（必填）
	 * @param shutUpTime 需禁言时间，单位为秒，为0时表示取消禁言
	 * @param userIds 需要禁言的用户帐号，最多支持500个帐号
	 * @return 操作结果
	 */
	public GroupMemberForbidSendMsgResponse updateGroupMemberShutUpTime(String groupId, Integer shutUpTime, String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Members_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("ShutUpTime", Objects.nonNull(shutUpTime) ? shutUpTime : 0)
				.build();
		return super.request(TimApiAddress.FORBID_SEND_MSG, requestBody, GroupMemberForbidSendMsgResponse.class);
	}

	/**
	 * 38、获取被禁言群成员列表
	 * API：https://cloud.tencent.com/document/product/269/2925
	 * @param groupId 群组ID（必填）
	 * @return 操作结果
	 */
	public GroupMemberShuttedUinResponse getGroupShuttedUin(String groupId) {
		Map<String, Object> requestBody = ImmutableMap.of("GroupId", groupId);
		return super.request(TimApiAddress.GET_GROUP_SHUTTED_UIN, requestBody, GroupMemberShuttedUinResponse.class);
	}

	/**
	 * 39、在群组中发送普通消息
     * API：https://cloud.tencent.com/document/product/269/1629
     * @param groupId 群组ID（必填）
	 * @param random 随机数字，五分钟数字相同认为是重复消息
	 * @param offlinePushInfo 离线推送信息配置，详细可参阅 消息格式描述
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @return 操作结果
	 */
	public GroupMsgSendResponse sendGroupMsg(String groupId, String random, OfflinePushInfo offlinePushInfo, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Random", random)
				.put("MsgBody", msgBody)
				.put("OfflinePushInfo", offlinePushInfo)
				.build();
		return super.request(TimApiAddress.SEND_GROUP_MSG, requestBody, GroupMsgSendResponse.class);
	}

	/**
	 * 40、在群组中发送普通消息
     * API：https://cloud.tencent.com/document/product/269/1629
     * @param groupId 群组ID（必填）
	 * @param random 随机数字，五分钟数字相同认为是重复消息
	 * @param forbidCallbackControl 消息回调禁止开关，只对单条消息有效，ForbidBeforeSendMsgCallback 表示禁止发消息前回调，ForbidAfterSendMsgCallback 表示禁止发消息后回调
	 * @param offlinePushInfo 离线推送信息配置，详细可参阅 消息格式描述
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @return 操作结果
	 */
	public GroupMsgSendResponse sendGroupMsg(String groupId, String random, String[] forbidCallbackControl, OfflinePushInfo offlinePushInfo, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Random", random)
				.put("ForbidCallbackControl", forbidCallbackControl)
				.put("OfflinePushInfo", offlinePushInfo)
				.put("MsgBody", msgBody)
				.build();
		return super.request(TimApiAddress.SEND_GROUP_MSG, requestBody, GroupMsgSendResponse.class);
	}

	/**
	 * 41、在群组中发送普通消息-指定消息不存离线及漫游
	 *
     * API：https://cloud.tencent.com/document/product/269/1629
     * @param groupId 群组ID（必填）
	 * @param random 随机数字，五分钟数字相同认为是重复消息
	 * @param onlineOnlyFlag 如果消息体中指定 OnlineOnlyFlag，只要值大于0，则消息表示只在线下发，不存离线和漫游（AVChatRoom 和 BChatRoom 不允许使用）。
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @return 操作结果
	 */
	public GroupMsgSendResponse sendGroupMsg(String groupId, String random, String onlineOnlyFlag, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Random", random)
				.put("OnlineOnlyFlag", onlineOnlyFlag)
				.put("MsgBody", msgBody)
				.build();
		return super.request(TimApiAddress.SEND_GROUP_MSG, requestBody, GroupMsgSendResponse.class);
	}

	/**
	 * 42、在群组中发送普通消息-指定消息不存离线及漫游
	 *
     * API：https://cloud.tencent.com/document/product/269/1629
     * @param groupId 群组ID（必填）
	 * @param random 随机数字，五分钟数字相同认为是重复消息
	 * @param onlineOnlyFlag 如果消息体中指定 OnlineOnlyFlag，只要值大于0，则消息表示只在线下发，不存离线和漫游（AVChatRoom 和 BChatRoom 不允许使用）
	 * @param forbidCallbackControl 消息回调禁止开关，只对单条消息有效，ForbidBeforeSendMsgCallback 表示禁止发消息前回调，ForbidAfterSendMsgCallback 表示禁止发消息后回调
	 * @param offlinePushInfo 离线推送信息配置，详细可参阅 消息格式描述
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @return 操作结果
	 */
	public GroupMsgSendResponse sendGroupMsg(String groupId, String random, String onlineOnlyFlag, String[] forbidCallbackControl, OfflinePushInfo offlinePushInfo, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Random", random)
				.put("OnlineOnlyFlag", onlineOnlyFlag)
				.put("ForbidCallbackControl", forbidCallbackControl)
				.put("OfflinePushInfo", offlinePushInfo)
				.put("MsgBody", msgBody)
				.build();
		return super.request(TimApiAddress.SEND_GROUP_MSG, requestBody, GroupMsgSendResponse.class);
	}

	/**
	 * 43、在群组中发送普通消息-指定消息发送者
	 * 可以指定某一位群成员作为消息发送者，在 From_Account 中设置。其他用户收到群消息后，显示的消息来自 App 管理员指定的那一位群成员。
     * API：https://cloud.tencent.com/document/product/269/1629
     * @param groupId 群组ID（必填）
     * @param userId 消息来源帐号ID。如果不填写该字段，则默认消息的发送者为调用该接口时使用的 App 管理员帐号。除此之外，App 亦可通过该字段“伪造”消息的发送者，从而实现一些特殊的功能需求。需要注意的是，如果指定该字段，必须要确保字段中的帐号是存在的
	 * @param random 随机数字，五分钟数字相同认为是重复消息
	 * @param msgBody 消息体，详细可参阅 消息格式描述
	 * @return 操作结果
	 */
	public GroupMsgSendResponse sendGroupProxyMsg(String groupId, String userId, String random, MsgBody... msgBody) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("From_Account", this.getImUserByUserId(userId))
				.put("Random", random)
				.put("MsgBody", msgBody)
				.build();
		return super.request(TimApiAddress.SEND_GROUP_MSG, requestBody, GroupMsgSendResponse.class);
	}

	/**
	 * 44、在群组中发送普通消息
	 * API：https://cloud.tencent.com/document/product/269/1629
	 * @param message 消息对象
	 * @return 操作结果
	 */
	public GroupMsgSendResponse sendGroupMsg(GroupMessage message) {
		return super.request(TimApiAddress.SEND_GROUP_MSG, message, GroupMsgSendResponse.class);
	}

	/**
	 * 45、在群组中发送系统通知
     * API：https://cloud.tencent.com/document/product/269/1630
     * @param groupId 群组ID（必填）
	 * @param content 系统通知的内容
	 * @return 操作结果
	 */
	public GroupSystemNotificationSendResponse sendGroupSysMsg(String groupId, String content) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Content", content)
				.build();
		return super.request(TimApiAddress.SEND_GROUP_SYSTEM_NOTIFICATION, requestBody, GroupSystemNotificationSendResponse.class);
	}

	/**
	 * 46、在群组中发送系统通知
     * API：https://cloud.tencent.com/document/product/269/1630
     * @param groupId 群组ID（必填）
	 * @param userIds 接收者群成员列表，请填写接收者 UserID，不填或为空表示全员下发
	 * @param content 系统通知的内容
	 * @return 操作结果
	 */
	public GroupSystemNotificationSendResponse sendGroupSysMsg(String groupId, String[] userIds, String content) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("ToMembers_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("Content", content)
				.build();
		return super.request(TimApiAddress.SEND_GROUP_SYSTEM_NOTIFICATION, requestBody, GroupSystemNotificationSendResponse.class);
	}

	/**
	 * 47、转让群主
     * API：https://cloud.tencent.com/document/product/269/1633
     * @param groupId 要被转移的群 ID（必填）
     * @param userId 新群主 ID（必填）
	 * @return 操作结果
	 */
	public GroupChangeOwnerResponse changeGroupOwner(String groupId, String userId) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("NewOwner_Account", this.getImUserByUserId(userId))
				.build();
		return super.request(TimApiAddress.CHANGE_GROUP_OWNER, requestBody, GroupChangeOwnerResponse.class);
	}

	/**
	 * 48、撤回群消息
     * API：https://cloud.tencent.com/document/product/269/12341
     * @param groupId 要被转移的群 ID（必填）
     * @param msgSeqs 被撤回的消息 seq 数组，一次请求最多可以撤回10条消息 seq
	 * @return 操作结果
	 */
	public GroupMsgRecallResponse recallGroupMsg(String groupId, Integer... msgSeqs) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("MsgSeqList", Stream.of(msgSeqs).map(msgSeq -> {
					Map<String, Object> msgSeqMap = new HashMap<>();
					msgSeqMap.put("MsgSeq", msgSeq);
					return msgSeqMap;
				}).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.GROUP_MSG_RECALL, requestBody, GroupMsgRecallResponse.class);
	}

	/**
	 * 49、导入群基础资料
	 * App 管理员可以通过该接口导入群组，不会触发回调、不会下发通知；当 App 需要从其他即时通信系统迁移到即时通信 IM 时，使用该协议导入存量群组数据。
	 * API：https://cloud.tencent.com/document/product/269/1634
	 * @param userId 需要修改基础信息的群组的 ID
	 * @param groupType 群组类型，包括 Public（陌生人社交群），Private（同新版本中的Work，好友工作群）， ChatRoom（同新版本中的Meeting，会议群）
	 * @param groupName  群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
	 * @param createTime  群组的创建时间（选填，不填会以请求时刻为准）
	 * @return 操作结果
	 */
	public GroupMsgImportResponse importGroup(String userId, String groupType, String groupName, Integer createTime) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Owner_Account", this.getImUserByUserId(userId))
				.put("Type", groupType)
				.put("Name", groupName)
				.put("CreateTime", createTime)
				.build();
		return super.request(TimApiAddress.IMPORT_GROUP, requestBody, GroupMsgImportResponse.class);
	}

	/**
	 * 50、导入群基础资料
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
	 * @return 操作结果
	 */
	public GroupMsgImportResponse importGroup(String userId, String groupId, String groupType, String groupName,
			String introduction, String notification, String faceUrl, Integer maxMemberCount, String applyJoinOption,
			List<MapKV> appDefinedData, Integer createTime) {
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
		return super.request(TimApiAddress.IMPORT_GROUP, requestBody, GroupMsgImportResponse.class);
	}

	/**
	 * 51、导入群消息
	 * API：https://cloud.tencent.com/document/product/269/1635
	 * @param groupId 要导入消息的群 ID
	 * @param msgList 导入的消息列表
	 * @return 操作结果
	 */
	public GroupMsgImportResponse importGroupMsg(String groupId, GroupMessageImport... msgList) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("MsgList", msgList)
				.build();
		return super.request(TimApiAddress.IMPORT_GROUP_MSG, requestBody, GroupMsgImportResponse.class);
	}

	/**
	 * 52、导入群成员
	 * API：https://cloud.tencent.com/document/product/269/1636
	 * @param groupId 要导入消息的群 ID
	 * @param memberList 导入的消息列表
	 * @return 操作结果
	 */
	public GroupMemberImportResponse importGroupMember(String groupId, GroupMemberImport... memberList) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("MemberList", memberList)
				.build();
		return super.request(TimApiAddress.IMPORT_GROUP_MSG, requestBody, GroupMemberImportResponse.class);
	}

	/**
	 * 52、设置成员未读消息计数
	 * API：https://cloud.tencent.com/document/product/269/1637
	 * @param groupId 操作的群 ID
	 * @param userId 要操作的群成员 ID
	 * @param unreadMsgNum 成员未读消息数
	 * @return 操作结果
	 */
	public GroupMsgUnreadNumResponse setGroupMemberUnreadMsgNum(String groupId, String userId, Integer unreadMsgNum) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Member_Account", this.getImUserByUserId(userId))
				.put("UnreadMsgNum", unreadMsgNum)
				.build();
		return super.request(TimApiAddress.SET_UNREAD_MSG_NUM, requestBody, GroupMsgUnreadNumResponse.class);
	}

	/**
	 * 54、撤回指定用户发送的消息
	 * API：https://cloud.tencent.com/document/product/269/2359
	 * @param groupId 要撤回消息的群 ID
	 * @param userId 被撤回消息的发送者 ID
	 * @return 操作结果
	 */
	public GroupMsgUnreadNumResponse recallGroupMemberMsg(String groupId, String userId) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("Sender_Account", this.getImUserByUserId(userId))
				.build();
		return super.request(TimApiAddress.DELETE_GROUP_MSG_BY_SENDER, requestBody, GroupMsgUnreadNumResponse.class);
	}

	/**
	 * 55、拉取群历史消息
	 * API：https://cloud.tencent.com/document/product/269/2738
	 * @param groupId 要拉取历史消息的群组 ID
	 * @param reqMsgSeq 拉取消息的最大 seq
	 * @return 操作结果
	 */
	public GroupMsgGetResponse getGroupMsg(String groupId, Integer reqMsgSeq) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("ReqMsgSeq", reqMsgSeq)
				.put("ReqMsgNumber", 20)
				.build();
		return super.request(TimApiAddress.GROUP_MSG_GET_SIMPLE, requestBody, GroupMsgGetResponse.class);
	}

	/**
	 * 56、拉取群历史消息
	 * API：https://cloud.tencent.com/document/product/269/2738
	 * @param groupId 要拉取历史消息的群组 ID
	 * @param reqMsgSeq 拉取消息的最大 seq
	 * @param reqMsgNumber 拉取的历史消息的条数，目前一次请求最多返回20条历史消息，所以这里最好小于等于20
	 * @return 操作结果
	 */
	public GroupMsgGetResponse getGroupMsg(String groupId, Integer reqMsgSeq, Integer reqMsgNumber) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("GroupId", groupId)
				.put("ReqMsgSeq", reqMsgSeq)
				.put("ReqMsgNumber", reqMsgNumber)
				.build();
		return super.request(TimApiAddress.GROUP_MSG_GET_SIMPLE, requestBody, GroupMsgGetResponse.class);
	}

	/**
	 * 57、获取直播群在线人数：App 管理员可以根据群组 ID 获取直播群在线人数。
	 * API：https://cloud.tencent.com/document/product/269/49180
	 * @param groupId 要撤回消息的群 ID
	 * @return 操作结果
	 */
	public GroupMemberOnlineNumGetResponse getGroupMemberOnlineNum(String groupId) {
		Map<String, Object> requestBody = ImmutableMap.of("GroupId", groupId);
		return super.request(TimApiAddress.GET_ONLINE_MEMBER_NUM, requestBody, GroupMemberOnlineNumGetResponse.class);
	}
}
