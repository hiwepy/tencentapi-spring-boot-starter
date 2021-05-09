package com.tencentcloud.spring.boot.tim;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.tencentcloud.spring.boot.tim.resp.ops.AppInfoResponse;
import com.tencentcloud.spring.boot.tim.resp.ops.AppIpResponse;

/**
 *  运营管理
 * https://cloud.tencent.com/document/product/269/1519
 */
public class TencentTimOpsOperations extends TencentTimOperations {

	public TencentTimOpsOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}
	
	/**
	 * 1、拉取运营数据
	 * API：https://cloud.tencent.com/document/product/269/4193
	 * @return 操作结果
	 */
	public AppInfoResponse getAppInfo() {
		return this.getAppInfo(null);
	}
	
	/**
	 * 1、拉取运营数据
	 * API：https://cloud.tencent.com/document/product/269/4193
	 * @param friendItems 该字段用来指定需要拉取的运营数据，不填默认拉取所有字段。详细可参阅下文可拉取的运营字段
	 * @return 操作结果
	 */
	public AppInfoResponse getAppInfo(List<String> friendItems) {
		Map<String, Object> requestBody = CollectionUtils.isEmpty(friendItems) ? Maps.newHashMap() : new ImmutableMap.Builder<String, Object>()
				.put("RequestField", friendItems)
				.build();
		return super.request(TimApiAddress.GET_APP_INFO, requestBody, AppInfoResponse.class);
	}
	
	/**
	 * 2、获取服务器 IP 地址
	 * API：https://cloud.tencent.com/document/product/269/45438
	 * @return 操作结果
	 */
	public AppIpResponse getIPList() {
		return super.request(TimApiAddress.GET_IP_LIST, Maps.newHashMap(), AppIpResponse.class);
	}
	
}
