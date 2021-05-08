package com.tencentcloud.spring.boot.tim;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.group.AppGroupGetResponse;

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
	 * @param limit 本次获取的群组 ID 数量的上限，不得超过 10000。如果不填，默认为最大值 10000
	 * @return 操作结果
	 */
	public AppGroupGetResponse getAppGroupList(Integer limit) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Limit", limit)
				.build();
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
	 * @param limit 本次获取的群组 ID 数量的上限，不得超过 10000。如果不填，默认为最大值 10000
	 * @param next  群太多时分页拉取标志，第一次填0，以后填上一次返回的值，返回的 Next 为0代表拉完了
	 * @param groupType 如果仅需要返回特定群组形态的群组，可以通过 GroupType 进行过滤，但此时返回的 TotalCount 的含义就变成了 App 中属于该群组形态的群组总数。不填为获取所有类型的群组。
	 * 群组形态包括 Public（公开群），Private（私密群），ChatRoom（聊天室），AVChatRoom（音视频聊天室）和 BChatRoom（在线成员广播大群）
	 * @return 操作结果
	 */
	public AppGroupGetResponse getAppGroupList(Integer limit, Integer next, String groupType) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Limit", limit)
				.put("Next", next)
				.put("GroupType", groupType)
				.build();
		return super.request(TimApiAddress.GET_APPID_GROUP_LIST, requestBody, AppGroupGetResponse.class);
	}
	
}
