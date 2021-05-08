package com.tencentcloud.spring.boot.tim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

/**
 * 关系链管理
 * https://cloud.tencent.com/document/product/269/1519
 */
public class TencentTimSnsOperations extends TencentTimOperations {

	public TencentTimSnsOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}
	
	/**
	 * 1、添加好友
	 * API：https://cloud.tencent.com/document/product/269/1643
	 * @param userId 业务用户ID
	 * @param addType  加好友方式（默认双向加好友方式）：Add_Type_Single 表示单向加好友, Add_Type_Both 表示双向加好友
	 * @param forceAdd 是否强制相互添加好友
	 * @param friends 添加的好友数组
	 * @return 操作结果
	 */
	public FriendAddResponse addFriend(String userId, String addType, boolean forceAdd, FriendAddItem ... friends) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("AddFriendItem", Stream.of(friends).map(friend -> {
					friend.setAccount(this.getImUserByUserId(friend.getAccount()));
					return friend;
				}).collect(Collectors.toList()))
				.put("AddType", addType)
				.put("ForceAddFlags", forceAdd ? 1 : 0)
				.build();
		return super.request(TimApiAddress.FRIEND_ADD, requestBody, FriendAddResponse.class);
	}
	
	/**
	 * 2、导入好友
	 * API：https://cloud.tencent.com/document/product/269/8301
	 * @param userId 业务用户ID
	 * @param friends 导入的好友数组
	 * @return 操作结果
	 */
	public FriendImportResponse importFriend(String userId, FriendImportItem ... friends) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("AddFriendItem", Stream.of(friends).map(friend -> {
					friend.setAccount(this.getImUserByUserId(friend.getAccount()));
					return friend;
				}).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.FRIEND_IMPORT, requestBody, FriendImportResponse.class);
	}
	

	/**
	 * 3、更新好友
	 * API：https://cloud.tencent.com/document/product/269/12525
	 * @param userId 业务用户ID
	 * @param friends 需要更新的好友对象数组
	 * @return 操作结果
	 */
	public FriendUpdateResponse updateFriend(String userId, FriendUpdateItem ... friends) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("UpdateItem", Stream.of(friends).map(friend -> {
					friend.setAccount(this.getImUserByUserId(friend.getAccount()));
					return friend;
				}).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.FRIEND_UPDATE, requestBody, FriendUpdateResponse.class);
	}
	
	/**
	 * 4、删除好友
	 * API：https://cloud.tencent.com/document/product/269/1644
	 * @param userId 业务用户ID
	 * @param deleteType 删除模式；
	 * <pre>
	 * 	单向删除好友 	Delete_Type_Single 	只将 To_Account 从 From_Account 的好友表中删除，但不会将 From_Account 从 To_Account 的好友表中删除
	 * 	双向删除好友 	Delete_Type_Both 	将 To_Account 从 From_Account 的好友表中删除，同时将 From_Account 从 To_Account 的好友表中删除
	 * <pre/>
	 * @param friends 待删除的好友的 UserID 列表，单次请求的 To_Account 数不得超过1000
	 * @return 操作结果
	 */
	public FriendDeleteResponse deleteFriend(String userId, String deleteType ,String ... friends) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("To_Account", Stream.of(friends).map(friend -> {
					return this.getImUserByUserId(friend);
				}).collect(Collectors.toList()))
				.put("DeleteType", deleteType)
				.build();
		return super.request(TimApiAddress.FRIEND_DELETE, requestBody, FriendDeleteResponse.class);
	}

	/**
	 * 5、删除所有好友
	 * API：https://cloud.tencent.com/document/product/269/1645
	 * @param userId 业务用户ID
	 * @param deleteType 删除模式；
	 * <pre>
	 * 	单向删除好友 	Delete_Type_Single 	只将 To_Account 从 From_Account 的好友表中删除，但不会将 From_Account 从 To_Account 的好友表中删除
	 * 	双向删除好友 	Delete_Type_Both 	将 To_Account 从 From_Account 的好友表中删除，同时将 From_Account 从 To_Account 的好友表中删除
	 * <pre/>
	 * @return 操作结果
	 */
	public FriendDeleteAllResponse deleteAllFriend(String userId, String deleteType) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("DeleteType", deleteType)
				.build(); 
		return super.request(TimApiAddress.FRIEND_DELETE_ALL, requestBody, FriendDeleteAllResponse.class);
	}
	
	/**
	 * 6、校验好友
	 * API：https://cloud.tencent.com/document/product/269/1646
	 * @param userId 业务用户ID
	 * @param checkType 校验模式； https://cloud.tencent.com/document/product/269/1501#.E6.A0.A1.E9.AA.8C.E5.A5.BD.E5.8F.8B
	 * @param friends 待删除的好友的 UserID 列表，单次请求的 To_Account 数不得超过1000
	 * @return 操作结果
	 */
	public FriendCheckResponse checkFriend(String userId, String checkType ,String ... friends) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("To_Account", Stream.of(friends).map(friend -> {
					return this.getImUserByUserId(friend);
				}).collect(Collectors.toList()))
				.put("CheckType", checkType)
				.build();
		return super.request(TimApiAddress.FRIEND_CHECK, requestBody, FriendCheckResponse.class);
	}
	
	/**
	 * 7、拉取好友
	 * API：https://cloud.tencent.com/document/product/269/1647
	 * @param userId 业务用户ID
	 * @param startIndex 分页的起始位置
	 * @param standardSequence 上次拉好友数据时返回的 StandardSequence，如果 StandardSequence 字段的值与后台一致，后台不会返回标配好友数据
	 * @param customSequence 上次拉好友数据时返回的 CustomSequence，如果 CustomSequence 字段的值与后台一致，后台不会返回自定义好友数据
	 * @return 操作结果
	 */
	public FriendGetResponse getFriends(String userId, Integer startIndex, Integer standardSequence, Integer customSequence) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("StartIndex", startIndex)
				.put("StandardSequence", standardSequence)
				.put("CustomSequence", customSequence)
				.build();
		return super.request(TimApiAddress.FRIEND_GET, requestBody, FriendGetResponse.class);
	}
	
	/**
	 * 7、拉取好友
	 * API：https://cloud.tencent.com/document/product/269/1647
	 * @param fromUserId 业务用户ID
	 * @param userIds 业务用户ID数组
	 * @return 操作结果
	 */
	public FriendGetListResponse getFriends(String fromUserId, String... userIds) {
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
		return this.getFriends(fromUserId, tagList, userIds);
	}
	
	/**
	 * 8、拉取好友
	 * API：https://cloud.tencent.com/document/product/269/1647
	 * @param fromUserId 业务用户ID
	 * @param tagList 指定要拉取的资料字段的 Tag，支持的字段有： 标配资料字段，自定义资料字段
	 * @param userIds 业务用户ID数组
	 * @return 操作结果
	 */
	public FriendGetListResponse getFriends(String fromUserId, List<String> tagList, String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("TagList", tagList)
				.build();
		return super.request(TimApiAddress.FRIEND_GET, requestBody, FriendGetListResponse.class);
	}
	
	/**
	 * 9、添加黑名单
	 * API：https://cloud.tencent.com/document/product/269/3718
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	  * @param fromUserId 需要添加该 UserID 的黑名单
	 * @param userIds 待添加的黑名单的 UserID 列表，单次请求的 To_Account 数不得超过1000
	 * @return 操作结果
	 */
	public BlacklistAddResponse addBlackList(String fromUserId, String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.BLACK_LIST_ADD, requestBody, BlacklistAddResponse.class);
	}
	
	/**
	 * 10、删除黑名单
	 * API：https://cloud.tencent.com/document/product/269/3719
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param fromUserId 需要删除该 UserID 的黑名单
	 * @param userIds 待删除的黑名单的 UserID 列表，单次请求的 To_Account 数不得超过1000
	 * @return 操作结果
	 */
	public BlacklistDeleteResponse deleteBlackList(String fromUserId, String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.BLACK_LIST_DELETE, requestBody, BlacklistDeleteResponse.class);
	}
	
	/**
	 * 11、拉取黑名单
	 * API：https://cloud.tencent.com/document/product/269/3722
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param userId 需要拉取该 UserID 的黑名单
	 * @param startIndex 拉取的起始位置
	 * @param maxLimited 每页最多拉取的黑名单数
	 * @param lastSequence 上一次拉黑名单时后台返回给客户端的 Seq，初次拉取时为0
	 * @return 操作结果
	 */
	public BlacklistGetResponse getBlackList(String userId, Integer startIndex, Integer maxLimited, Integer lastSequence) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("StartIndex", startIndex)
				.put("MaxLimited", maxLimited)
				.put("LastSequence", lastSequence)
				.build();
		return super.request(TimApiAddress.BLACK_LIST_GET, requestBody, BlacklistGetResponse.class);
	}
	
	/**
	 * 12、校验黑名单
	 * API：https://cloud.tencent.com/document/product/269/3725
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param checkType 校验模式，详情可参见 校验黑名单
	 * @param fromUserId 需要校验该 UserID 的黑名单
	 * @param userIds 待校验的黑名单的 UserID 列表，单次请求的 To_Account 数不得超过1000
	 * @return 操作结果
	 */
	public BlacklistCheckResponse checkBlackList(String checkType, String fromUserId, String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.put("CheckType", checkType)
				.build();
		return super.request(TimApiAddress.BLACK_LIST_CHECK, requestBody, BlacklistCheckResponse.class);
	}
	
	/**
	 * 12、添加分组
	 * API：https://cloud.tencent.com/document/product/269/10107
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param fromUserId 需要为该 UserID 添加新分组
	 * @param groupNames 新增分组列表
	 * @return 操作结果
	 */
	public GroupAddResponse addGroup(String fromUserId, String... groupNames) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("GroupName", groupNames)
				.build();
		return super.request(TimApiAddress.GROUP_ADD, requestBody, GroupAddResponse.class);
	}
	
	/**
	 * 12、添加分组
	 * API：https://cloud.tencent.com/document/product/269/10107
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param fromUserId 需要为该 UserID 添加新分组
	 * @param groupNames 新增分组列表
	 * @param userIds 需要加入新增分组的好友的 UserID 列表
	 * @return 操作结果
	 */
	public GroupAddResponse addGroup(String fromUserId, String[] groupNames, String... userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("GroupName", groupNames)
				.put("To_Account", Stream.of(userIds).map(uid -> this.getImUserByUserId(uid)).collect(Collectors.toList()))
				.build();
		return super.request(TimApiAddress.GROUP_ADD, requestBody, GroupAddResponse.class);
	}
	
	/**
	 * 13、删除分组
	 * API：https://cloud.tencent.com/document/product/269/3719
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param fromUserId 需要删除该 UserID 的黑名单
	 * @param groupNames 要删除的分组列表
	 * @return 操作结果
	 */
	public GroupDeleteResponse deleteGroups(String fromUserId, String... groupNames) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(fromUserId))
				.put("GroupName", groupNames)
				.build();
		return super.request(TimApiAddress.GROUP_DELETE, requestBody, GroupDeleteResponse.class);
	}
	
	/**
	 * 14、拉取分组
	 * API：https://cloud.tencent.com/document/product/269/3722
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param userId 指定要拉取分组的用户的 UserID
	 * @param lastSequence 上一次拉取分组时后台返回给客户端的 Seq，初次拉取时为0，只有 GroupName 为空时有效
	 * @param groupNames 要拉取的分组名称
	 * @return 操作结果
	 */
	public GroupGetResponse getGroups(String userId, Integer lastSequence, String... groupNames) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("LastSequence", lastSequence)
				.put("GroupName", groupNames)
				.build();
		return super.request(TimApiAddress.GROUP_GET, requestBody, GroupGetResponse.class);
	}
	
	/**
	 * 15、拉取分组
	 * API：https://cloud.tencent.com/document/product/269/3722
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param userId 指定要拉取分组的用户的 UserID
	 * @param lastSequence 上一次拉取分组时后台返回给客户端的 Seq，初次拉取时为0，只有 GroupName 为空时有效
	 * @param needFriend 是否需要拉取分组下的 User 列表, Need_Friend_Type_Yes: 需要拉取, 不填时默认不拉取, 只有 GroupName 为空时有效
	 * @param groupNames 要拉取的分组名称
	 * @return 操作结果
	 */
	public GroupGetResponse getGroups(String userId, Integer lastSequence, String needFriend, String... groupNames) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("LastSequence", lastSequence)
				.put("NeedFriend", needFriend)
				.put("GroupName", groupNames)
				.build();
		return super.request(TimApiAddress.GROUP_GET, requestBody, GroupGetResponse.class);
	}
	
}
