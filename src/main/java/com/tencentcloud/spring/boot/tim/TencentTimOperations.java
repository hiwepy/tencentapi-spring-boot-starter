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

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.BeanUtils;

import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;
import com.tencentcloud.spring.boot.utils.CommonHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * Base class for the Tencent Cloud IM (TIM) operation groupings (account,
 * group, profile, sns, etc.). Subclasses delegate request execution to the
 * shared {@link TencentTimTemplate} and inherit common helpers for signature
 * generation, user-id translation and synchronous/asynchronous invocation.
 * @see <a href="https://cloud.tencent.com/document/product/269/42440">TIM REST API overview</a>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Slf4j
public abstract class TencentTimOperations {

	/** Base URL prefix for all TIM REST API endpoints. */
	public static final String PREFIX = "https://console.tim.qq.com";
	/** HTTP {@code Content-Type} for plain JSON. */
	public static final String APPLICATION_JSON_VALUE = "application/json";
	/** HTTP {@code Content-Type} for UTF-8 JSON. */
	public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

	/** The backing template used to execute requests. */
	protected TencentTimTemplate timTemplate;

	/**
	 * Binds this operation helper to the given template.
	 *
	 * @param timTemplate the backing TIM template
	 */
	public TencentTimOperations(TencentTimTemplate timTemplate) {
		this.timTemplate = timTemplate;
	}

	/**
	 * Generates a user signature for the given identifier using the configured
	 * default expiry.
	 *
	 * @param identifier the TIM account identifier
	 * @return the generated UserSig
	 */
	protected String genUserSig(String identifier) {
		return timTemplate.genUserSig(identifier);
	}

	/**
	 * Generates a user signature for the given identifier with a custom expiry.
	 *
	 * @param identifier the TIM account identifier
	 * @param expire     signature validity in seconds
	 * @return the generated UserSig
	 */
	protected String genUserSig(String identifier, long expire) {
		return timTemplate.genUserSig(identifier, expire);
	}

	/**
	 * Resolves the application user id for the given TIM account.
	 *
	 * @param imUser the TIM account identifier
	 * @return the application user id
	 */
	protected String getUserIdByImUser(String imUser) {
		return timTemplate.getUserIdByImUser(imUser);
	}

	/**
	 * Resolves the TIM account for the given application user id.
	 *
	 * @param userId the application user id
	 * @return the TIM account identifier
	 */
	protected String getImUserByUserId(String userId) {
		return timTemplate.getImUserByUserId(userId);
	}

	/**
	 * Returns the default TIM REST API query parameters (UserSig, identifier,
	 * SdkAppid, random, contenttype).
	 *
	 * @return the default query parameters
	 */
	protected Map<String, String> getDefaultParams() {
		return getTimTemplate().getDefaultParams();
	}

	/**
	 * Synchronously invokes a TIM REST API endpoint and returns the parsed
	 * response, logging success or failure.
	 *
	 * @param address the TIM API address enum constant
	 * @param params  the request body object (serialized to JSON)
	 * @param cls     the response type
	 * @param <T>     the response type, extending {@link TimActionResponse}
	 * @return the parsed response
	 */
	protected <T extends TimActionResponse> T request(TimApiAddress address, Object params, Class<T> cls) {
		String url = CommonHelper.getRequestUrl(address, getDefaultParams());
		T res =  getTimTemplate().requestInvoke(url, params, cls);
		if (res.isSuccess()) {
			log.info("Tim {} >> Success, url : {}, params : {}, ActionStatus : {}", address.getOpt(), url, params, res.getActionStatus());
		} else {
			log.error("Tim {} >> Failure, url : {}, params : {}, ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", address.getOpt(), url, params, res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
		}
		return res;
	}
	
	/**
	 * Asynchronously invokes a TIM REST API endpoint and delivers the parsed
	 * response to the supplied callback.
	 *
	 * @param address the TIM API address enum constant
	 * @param params  the request body object (serialized to JSON)
	 * @param cls     the response type
	 * @param consumer callback invoked with the parsed response
	 * @param <T>     the response type, extending {@link TimActionResponse}
	 */
	protected <T extends TimActionResponse> void asyncRequest(TimApiAddress address, Object params, Class<T> cls, Consumer<T> consumer) {
		String url = CommonHelper.getRequestUrl(address, getDefaultParams());
		getTimTemplate().requestAsyncInvoke(url, params, (response) -> {
			if (response.isSuccessful()) {
				try {
					String body = response.body().string();
					T res = getTimTemplate().readValue(body, cls);
					if (res.isSuccess()) {
						log.info("Tim {} >> Success, url : {}, params : {}, ActionStatus : {}, Body : {}", address.getOpt(), url, params, res.getActionStatus(), body);
					} else {
						log.error("Tim {} >> Failure, url : {}, params : {}, ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", url, params, res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
					}
					consumer.accept(res);
				} catch (IOException e) {
					log.error("Response Parse Error : {}", e.getMessage());
					T res = BeanUtils.instantiateClass(cls);
					consumer.accept(res);
				}
            } else {
            	T res = BeanUtils.instantiateClass(cls);
				consumer.accept(res);
            }
		});
	}
	
	/** @return the backing TIM template. */
	public TencentTimTemplate getTimTemplate() {
		return timTemplate;
	}

}
