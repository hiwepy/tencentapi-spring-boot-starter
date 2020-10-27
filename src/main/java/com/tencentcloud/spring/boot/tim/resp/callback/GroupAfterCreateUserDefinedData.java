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
package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 用户建群时的自定义字段
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupAfterCreateUserDefinedData {

	/**
	 * 用户建群时的自定义字段Key
	 */
	@JsonProperty(value = "Key")
	private String Key;
	/**
	 * 用户建群时的自定义字段Value
	 */
	@JsonProperty(value = "Value")
	private String Value;
}
