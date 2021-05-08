package com.tencentcloud.spring.boot.tim;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.req.sns.FriendAddItem;
import com.tencentcloud.spring.boot.tim.resp.sns.FriendAddResponse;

/**
 * 6、群组管理
 * https://cloud.tencent.com/document/product/269/1614
 */
public class TencentTimGroupOperations extends TencentTimOperations {

	public TencentTimGroupOperations(TencentTimTemplate timTemplate) {
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
	
}
