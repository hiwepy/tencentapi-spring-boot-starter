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
package com.tencentcloud.spring.boot.tim.resp.ops;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class AppInfoResult {
	
	/**
	 * 应用 SDKAppID
	 */
	@JsonProperty("AppId")
	private String appId;
	
	/**
	 * 应用名称
	 */
	@JsonProperty("AppName")
	private String appName;
	
	/**
	 * 所属客户名称
	 */
	@JsonProperty("Company")
	private String company;
	
	/**
	 * 活跃用户数
	 */
	@JsonProperty("ActiveUserNum")
	private Integer activeUserNum;
	
	/**
	 * 新增注册人数
	 */
	@JsonProperty("RegistUserNumOneDay")
	private Integer registUserNumOneDay;
	
	/**
	 * 累计注册人数
	 */
	@JsonProperty("RegistUserNumTotal")
	private Integer registUserNumTotal;
	
	/**
	 * 登录次数
	 */
	@JsonProperty("LoginTimes")
	private Integer loginTimes;
	
	/**
	 * 登录人数
	 */
	@JsonProperty("LoginUserNum")
	private Integer loginUserNum;

	/**
	 * 上行消息数
	 */
	@JsonProperty("UpMsgNum")
	private Integer upMsgNum;
	 	
	/**
	 * 发消息人数
	 */
	@JsonProperty("SendMsgUserNum")
	private Integer sendMsgUserNum;
	
	/**
	 * APNs 推送数
	 */
	@JsonProperty("APNSMsgNum")
	private Integer apnSMsgNum;
	
	/**
	 * 上行消息数（C2C）
	 */
	@JsonProperty("C2CUpMsgNum")
	private Integer c2CUpMsgNum;
	
	/**
	 * 发消息人数（C2C）
	 */
	@JsonProperty("C2CSendMsgUserNum")
	private Integer c2CSendMsgUserNum;
	
	/**
	 * APNs 推送数（C2C）
	 */
	@JsonProperty("C2CAPNSMsgNum")
	private Integer c2CAPNSMsgNum;

	/**
	 * 最高在线人数
	 */
	@JsonProperty("MaxOnlineNum")
	private Integer maxOnlineNum;
	
	/**
	 * 关系链对数增加量
	 */
	@JsonProperty("ChainIncrease")
	private Integer chainIncrease;
	 	
	/**
	 * 关系链对数删除量
	 */
	@JsonProperty("ChainDecrease")
	private Integer chainDecrease;
 	
	/**
	 * 上行消息数（群）
	 */
	@JsonProperty("GroupUpMsgNum")
	private Integer groupUpMsgNum;
 	
	/**
	 * 发消息人数（群）
	 */
	@JsonProperty("GroupSendMsgUserNum")
	private Integer groupSendMsgUserNum;
	
 	
	/**
	 * APNs 推送数（群）
	 */
	@JsonProperty("GroupAPNSMsgNum")
	private Integer groupAPNSMsgNum;
	
	/**
	 * 发消息群组数
	 */
	@JsonProperty("GroupSendMsgGroupNum")
	private Integer groupSendMsgGroupNum;
	 	
	 	
	/**
	 * 入群总数
	 */
	@JsonProperty("GroupJoinGroupTimes")
	private Integer groupJoinGroupTimes;
	
	/**
	 * 退群总数
	 */
	@JsonProperty("GroupQuitGroupTimes")
	private Integer groupQuitGroupTimes;
	
	 	
	/**
	 * 新增群组数
	 */
	@JsonProperty("GroupNewGroupNum")
	private Integer groupNewGroupNum;
	
	/**
	 * 累计群组数
	 */
	@JsonProperty("GroupAllGroupNum")
	private Integer groupAllGroupNum;
	 	
	/**
	 * 解散群个数
	 */
	@JsonProperty("GroupDestroyGroupNum")
	private Integer groupDestroyGroupNum;
	
	
 	/**
	 * 回调请求数
	 */
	@JsonProperty("CallBackReq")
	private Integer callBackReq;
	
	/**
	 * 回调请求数
	 */
	@JsonProperty("CallBackRsp")
	private Integer callBackRsp;

	/**
	 * 日期
	 */
	@JsonProperty("Date")
	private String date;
	
	
}
