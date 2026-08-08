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
package com.tencentcloud.spring.boot.trtc;

/**
 * Strategy for translating between application user ids and Tencent TRTC
 * account identifiers.
 * <p>
 * Register a bean implementing this interface to map a TRTC account to the
 * corresponding application user id (and vice-versa) when the two namespaces
 * differ. The default implementations are identity functions, so applications
 * that use the same value for both can leave the interface unimplemented.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
public interface TrtcUserIdProvider {

	/**
	 * Resolves the application user id for a given TRTC account.
	 *
	 * @param sdkappid the TRTC application SdkAppid
	 * @param account  the TRTC account identifier
	 * @return the application user id (defaults to the account itself)
	 */
	default String getUserIdByTrtcUser(Long sdkappid, String account)  {
		return account;
	}

	/**
	 * Resolves the TRTC account for a given application user id.
	 *
	 * @param sdkappid the TRTC application SdkAppid
	 * @param userId   the application user id
	 * @return the TRTC account identifier (defaults to the user id itself)
	 */
	default String getTrtcUserByUserId(Long sdkappid, String userId) {
		return userId;
	}

}
