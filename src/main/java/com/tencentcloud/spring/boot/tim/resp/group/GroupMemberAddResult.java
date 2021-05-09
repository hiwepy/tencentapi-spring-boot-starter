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
package com.tencentcloud.spring.boot.tim.resp.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class GroupMemberAddResult {

    /**
     * 返回的群成员 UserID
     */
    @JsonProperty("Member_Account")
    private String account;

    /**
     * 加人结果：0-失败；1-成功；2-已经是群成员；3-等待被邀请者确认
     */
    @JsonProperty("Result")
    private Integer result = 0;
    
    
}