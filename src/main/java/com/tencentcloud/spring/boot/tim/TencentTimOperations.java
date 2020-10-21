/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
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

import java.util.Map;

import com.google.common.base.Joiner;

/**
 * Tim 接口集成
 * https://cloud.tencent.com/document/product/269/42440
 */
public abstract class TencentTimOperations {

	public static final String PREFIX = "https://console.tim.qq.com";
	public static final String APPLICATION_JSON_VALUE = "application/json";
	public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

	public static final String DELIMITER = "&";
	public static final String SEPARATOR = "=";
	
	protected final Joiner.MapJoiner joiner = Joiner.on(DELIMITER).withKeyValueSeparator(SEPARATOR);
 
	protected TencentTimTemplate timTemplate;

	public TencentTimOperations(TencentTimTemplate timTemplate) {
		this.timTemplate = timTemplate;
	}
	
	protected String genSig(String identifier) {
		return timTemplate.genSig(identifier);
	}

	protected String genSig(String identifier, long expire) {
		return timTemplate.genSig(identifier, expire);
	}

	protected String genSigWithUserBuf(String identifier, long expire, byte[] userbuf) {
		return timTemplate.genSigWithUserBuf(identifier, expire, userbuf);
	}

	/**
	 * 根据im用户id获取用户id
	 * 
	 * @param imUser
	 * @return
	 */
	protected String getUserIdByImUser(String imUser) {
		return timTemplate.getUserIdByImUser(imUser);
	}

	/**
	 * 根据用户id获取im用户id
	 * 
	 * @param userId
	 * @return
	 */
	protected String getImUserByUserId(String userId) {
		return timTemplate.getImUserByUserId(userId);
	}
	
	/**
	 * 返回默认的参数
	 * @return
	 */
	protected Map<String, String> getDefaultParams() {
		return getTimTemplate().getDefaultParams();
	}

	protected <T> T request(String url, Object params, Class<T> cls) {
		return getTimTemplate().toBean(getTimTemplate().requestInvoke(url, params), cls);
	}
	
	public TencentTimTemplate getTimTemplate() {
		return timTemplate;
	}
	
}
