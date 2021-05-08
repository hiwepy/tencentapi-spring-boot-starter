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
package com.tencentcloud.spring.boot.utils;

import java.util.Map;
import java.util.Objects;

import com.google.common.base.Joiner;
import com.tencentcloud.spring.boot.tim.TimApiAddress;

/**
 * This class gathers all the utilities methods.
 */
public final class CommonHelper {

	public static final String DELIMITER = "&";
	public static final String SEPARATOR = "=";
	public static final Joiner.MapJoiner joiner = Joiner.on(DELIMITER).withKeyValueSeparator(SEPARATOR);
	
	public static String getRequestUrl(final TimApiAddress address, final Map<String, String> data) {
		 if (Objects.nonNull(data) && !data.isEmpty()) {
			final StringBuilder sb = new StringBuilder();
			sb.append(address.getUrl());
			if (address.getUrl().indexOf("?") >= 0) {
	            sb.append("&");
	        } else {
	            sb.append("?");
	        }
			sb.append(joiner.join(data));
	        return sb.toString();
		}
		return address.getUrl();
	}
     
}