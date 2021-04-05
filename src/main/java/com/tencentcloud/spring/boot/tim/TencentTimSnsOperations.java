package com.tencentcloud.spring.boot.tim;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.req.message.BlacklistMessage;
import com.tencentcloud.spring.boot.tim.req.sns.FriendItem;
import com.tencentcloud.spring.boot.tim.resp.AccountCheckResponse;
import com.tencentcloud.spring.boot.tim.resp.AddFriendResponse;
import com.tencentcloud.spring.boot.tim.resp.BlacklistResponse;

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
	public AddFriendResponse addFriend(String userId, String addType, boolean forceAdd, FriendItem ... friends) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("From_Account", this.getImUserByUserId(userId))
				.put("AddFriendItem", Stream.of(friends).map(friend -> {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("To_Account", this.getImUserByUserId(friend.getAccount()));
					if(StringUtils.hasText(friend.getRemark())) {
						userMap.put("Remark", friend.getRemark());
					}
					if(StringUtils.hasText(friend.getGroupName())) {
						userMap.put("GroupName", friend.getGroupName());
					}
					userMap.put("AddSource", this.getImUserByUserId(friend.getAccount()));
					if(StringUtils.hasText(friend.getWording())) {
						userMap.put("AddWording", friend.getWording());
					}
					return userMap;
				}).collect(Collectors.toList()))
				.put("AddType", addType)
				.put("ForceAddFlags", forceAdd ? 1 : 0)
				.build();
		AddFriendResponse res = request(TimApiAddress.FRIEND_ADD.getValue() + joiner.join(getDefaultParams()),
				requestBody, AddFriendResponse.class);
		if (!res.isSuccess()) {
			log.error("添加好友失败， ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
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
