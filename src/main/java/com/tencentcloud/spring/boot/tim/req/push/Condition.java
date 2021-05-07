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
package com.tencentcloud.spring.boot.tim.req.push;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Condition 共有4种条件类型，分别是：
 * 
   *   属性的或条件 AttrsOr
    *     属性的与条件 AttrsAnd
   *     标签的或条件 TagsOr
    *     标签的与条件 TagsAnd
 * 	AttrsOr 和 AttrsAnd 可以并存，TagsOr 和 TagsAnd 也可以并存。但是标签和属性条件不能并存。如果没有 Condition，则推送给全部用户
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Condition {

	/**
	 * 属性条件的并集。注意属性推送和标签推送不可同时作为推送条件
	 */
	@JsonProperty("AttrsOr")
	private Map<String, String> attrsOr;

	/**
	 * 属性条件的交集。注意属性推送和标签推送不可同时作为推送条件
	 */
	@JsonProperty("AttrsAnd")
	private Map<String, String> attrsAnd;
	
	/**
	 * 标签条件的并集。标签是一个不超过50字节的字符串。注意属性推送和标签推送不可同时作为推送条件。TagsOr 条件中的标签个数不能超过10个
	 */
	@JsonProperty("TagsOr")
	private String[] tagsOr;
	
	/**
	 * 标签条件的交集。标签是一个不超过50字节的字符串。注意属性推送和标签推送不可同时作为推送条件。TagsAnd 条件中的标签个数不能超过10个
	 */
	@JsonProperty("TagsAnd")
	private String[] tagsAnd;
	
}
