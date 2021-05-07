package com.tencentcloud.spring.boot.tim;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.req.message.BlacklistMessage;
import com.tencentcloud.spring.boot.tim.req.sns.FriendAddItem;
import com.tencentcloud.spring.boot.tim.req.sns.FriendImportItem;
import com.tencentcloud.spring.boot.tim.req.sns.FriendUpdateItem;
import com.tencentcloud.spring.boot.tim.resp.BlacklistResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendAddResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendCheckResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendDeleteAllResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendDeleteResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendImportResponse;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendUpdateResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 关系链管理
 * https://cloud.tencent.com/document/product/269/1519
 */
@Slf4j
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
		FriendAddResponse res = request(TimApiAddress.FRIEND_ADD.getValue() + joiner.join(getDefaultParams()),
				requestBody, FriendAddResponse.class);
		if (!res.isSuccess()) {
			log.error("添加好友失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
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
		FriendImportResponse res = request(TimApiAddress.FRIEND_IMPORT.getValue() + joiner.join(getDefaultParams()),
				requestBody, FriendImportResponse.class);
		if (!res.isSuccess()) {
			log.error("导入好友失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
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
		FriendUpdateResponse res = request(TimApiAddress.FRIEND_UPDATE.getValue() + joiner.join(getDefaultParams()),
				requestBody, FriendUpdateResponse.class);
		if (!res.isSuccess()) {
			log.error("更新好友失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
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
		FriendDeleteResponse res = request(TimApiAddress.FRIEND_DELETE.getValue() + joiner.join(getDefaultParams()),
				requestBody, FriendDeleteResponse.class);
		if (!res.isSuccess()) {
			log.error("删除好友失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
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
		FriendDeleteAllResponse res = request(TimApiAddress.FRIEND_DELETE_ALL.getValue() + joiner.join(getDefaultParams()),
				requestBody, FriendDeleteAllResponse.class);
		if (!res.isSuccess()) {
			log.error("删除所有好友失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
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
		FriendCheckResponse res = request(TimApiAddress.FRIEND_CHECK.getValue() + joiner.join(getDefaultParams()),
				requestBody, FriendCheckResponse.class);
		if (!res.isSuccess()) {
			log.error("校验好友失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
	}
	
	public Boolean addBlackList(String fromAccount, String toAccount) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setToAccount(Arrays.asList(toAccount));

		BlacklistResponse res = request(
				TimApiAddress.BLACK_LIST_ADD.getValue() + joiner.join(getDefaultParams()), message,
				BlacklistResponse.class);
		if (!res.isSuccess()) {
			log.error("拉黑失败，ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
			return false;
		}
		return true;
	}

	public Boolean deleteBlackList(String fromAccount, String toAccount) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setToAccount(Arrays.asList(toAccount));

		BlacklistResponse res = request(
				TimApiAddress.BLACK_LIST_DELETE.getValue() + joiner.join(getDefaultParams()), message,
				BlacklistResponse.class);
		if (!res.isSuccess()) {
			log.error("取消拉黑失败，ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
			return false;
		}
		return true;
	}

	public BlacklistResponse getBlackList(String fromAccount, Integer lastSequence) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setStartIndex(0);
		message.setMaxLimited(20);
		message.setLastSequence(lastSequence);

		BlacklistResponse blacklistResponse = request(
				TimApiAddress.BLACK_LIST_GET.getValue() + joiner.join(getDefaultParams()), message,
				BlacklistResponse.class);
		return blacklistResponse;
	}
	
}
