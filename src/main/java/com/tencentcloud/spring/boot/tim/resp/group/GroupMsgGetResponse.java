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
package com.tencentcloud.spring.boot.tim.resp.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.req.message.MsgBody;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class GroupMsgGetResponse extends TimActionResponse {

	/**
	 * 请求中的群组 ID
	 */
	@JsonProperty("GroupId")
	private String groupId;
	/**
	 * 是否返回了请求区间的全部消息
	 * 当消息长度太长或者区间太大（超过20）导致无法返回全部消息时，值为0
	 * 当消息长度太长或者区间太大（超过20）且所有消息都过期时，值为2
	 */
	@JsonProperty("IsFinished")
	private String finished;
	/**
	 * 返回的消息列表
	 */ 
	@JsonProperty("RspMsgList")
	private List<GroupMsgGetResult> msgList;
	
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public class GroupMsgGetResult {
	
		/**
	     * 消息的发送者
	     */
	    @JsonProperty("From_Account")
	    private String account;
	    /**
	     * 是否是空洞消息，当消息被删除或者消息过期后，MsgBody 为空，该字段为1，撤回的消息，该字段为2
	     */
	    @JsonProperty("IsPlaceMsg")
	    private Integer isPlaceMsg;
	    /**
	     * 消息内容，详情请参见 消息内容 MsgBody 说明
	     */
	    @JsonProperty("MsgBody")
	    private List<MsgBody> msgBodys;
	    /**
	     * 消息的优先级，用于消息去重，有客户端发消息时填写，如果没有填，服务端会自动生成，1表示 High 优先级消息，2表示 Normal 优先级消息，3表示 Low 优先级消息，4表示 Lowest 优先级消息
	     */
	    @JsonProperty("MsgPriority")
	    private Integer msgPriority;
	    /**
	     * 消息随机值，用于对消息去重，有客户端发消息时填写，如果没有填，服务端会自动生成
	     */
	    @JsonProperty("MsgRandom")
	    private Integer msgRandom;
	    /**
	     * 消息 seq，用于标识唯一消息，值越小发送的越早
	     */
	    @JsonProperty("MsgSeq")
	    private Integer msgSeq;
	    /**
	     * 消息被发送的时间戳，server 的时间
	     */
	    @JsonProperty("MsgTimeStamp")
	    private Integer msgTimeStamp;
	    
	    
	    
	    
	}
	
}
