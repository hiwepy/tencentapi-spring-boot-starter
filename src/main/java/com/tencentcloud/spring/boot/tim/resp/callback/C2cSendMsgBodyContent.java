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
package com.tencentcloud.spring.boot.tim.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class C2cSendMsgBodyContent {

	/**
	 * 1、语音序列号，类型为 String。后台用于索引语音的键值。无法通过该字段下载相应的语音。若需要获取该语音，请升级 IM SDK 版本至4.X。
	 * 2、 图片序列号。后台用于索引图片的键值
	 */
	@JsonProperty(value = "UUID")
	private String uuid;
	/**
	 * 表情索引，用户自定义
	 */
	@JsonProperty(value = "Index")
	private int index;
	/**
	 * 消息内容
	 */
	@JsonProperty(value = "Text")
	private String text;
	/**
	 * 描述
	 */
	@JsonProperty(value = "Desc")
	private String desc;
	/**
	 *  额外数据、自定义消息数据
	 */
	@JsonProperty(value = "Data")
	private String data;
	/**
	 *  扩展字段
	 */
	@JsonProperty(value = "Ext")
	private String ext;
	/**
	 * 经度
	 */
	@JsonProperty(value = "Longitude")
	private Double longitude;
	/**
	 * 纬度
	 */
	@JsonProperty(value = "Latitude")
	private Double latitude;
	/**
	 *  自定义 APNs 推送铃音
	 */
	@JsonProperty(value = "Sound")
	private String sound;
	
	// -- 语音消息
	
	/**
	 *  语音下载地址，可通过该 URL 地址直接下载相应语音
	 */
	@JsonProperty(value = "Url")
	private String url;
	/**
	 *  语音时长，单位：秒
	 */
	@JsonProperty(value = "Second")
	private Long second;
	
	// -- 图片消息
    
	/**
	 * 图片格式。JPG = 1，GIF = 2，PNG = 3，BMP = 4，其他 = 255。
	 */
	@JsonProperty(value = "ImageFormat")
	private Integer imageFormat;
	/**
	 * 原图、缩略图或者大图下载信息
	 */
	@JsonProperty(value = "ImageInfoArray")
	private List<ImageInfoArray> imageInfoArray;
	
}
