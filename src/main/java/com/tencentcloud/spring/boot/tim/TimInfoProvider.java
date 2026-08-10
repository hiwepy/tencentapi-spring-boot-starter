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

/**
 * Strategy for resolving Tencent Cloud IM (TIM) options and translating between
 * application user ids and TIM account identifiers.
 * <p>
 * Register a bean implementing this interface to supply per-SdkAppid
 * {@link TencentTimOption} instances and to map TIM accounts to application
 * user ids (and vice-versa) when the two namespaces differ. The default
 * implementations are identity functions, so applications that use the same
 * value for both can leave the methods unimplemented.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public interface TimInfoProvider {

	/**
	 * Returns the {@link TencentTimOption} for the given SdkAppid.
	 *
	 * @param sdkAppId the TIM application SdkAppid
	 * @return the matching TIM options
	 */
	TencentTimOption getTimOptionBySdkAppId(Long sdkAppId);

	/**
	 * Resolves the application user id for a given TIM account.
	 *
	 * @param sdkAppId the TIM application SdkAppid
	 * @param account  the TIM account identifier
	 * @return the application user id (defaults to the account itself)
	 */
	default String getUserIdByImUser(Long sdkAppId, String account)  {
		return account;
	}

	/**
	 * Resolves the TIM account for a given application user id.
	 *
	 * @param sdkAppId the TIM application SdkAppid
	 * @param userId   the application user id
	 * @return the TIM account identifier (defaults to the user id itself)
	 */
	default String getImUserByUserId(Long sdkAppId, String userId) {
		return userId;
	}

}
