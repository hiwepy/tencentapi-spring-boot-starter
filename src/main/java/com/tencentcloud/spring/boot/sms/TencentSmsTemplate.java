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
package com.tencentcloud.spring.boot.sms;

import org.springframework.boot.context.properties.PropertyMapper;

import com.tencentcloud.spring.boot.TencentSmsProperties;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

public class TencentSmsTemplate {

	private SmsClient client;
	private TencentSmsProperties smsProperties;
	
	
	public TencentSmsTemplate(SmsClient client, TencentSmsProperties smsProperties) {
		this.client = client;
		this.smsProperties = smsProperties;
	}

	public SendSmsResponse send(String phoneNumber, String templateID, String[] templateParams) throws TencentCloudSDKException {
		return this.send(new String[] {phoneNumber}, templateID, templateParams);
	}
	
	public SendSmsResponse send(String[] phoneNumbers, String templateID, String[] templateParams) throws TencentCloudSDKException {
		
		/*
		 * 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数 您可以直接查询 SDK 源码确定接口有哪些属性可以设置
		 * 属性可能是基本类型，也可能引用了另一个数据结构 推荐使用 IDE 进行开发，可以方便地跳转查阅各个接口和数据结构的文档说明
		 */
		SendSmsRequest req = new SendSmsRequest();

		/* 模板 ID: 必须填写已审核通过的模板 ID，可登录 [短信控制台] 查看模板 ID */
		req.setTemplateID(templateID);

		/*
		 * 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号] 例如+8613711112222， 其中前面有一个+号
		 * ，86为国家码，13711112222为手机号，最多不要超过200个手机号
		 */
		req.setPhoneNumberSet(phoneNumbers);

		/* 模板参数: 若无模板参数，则设置为空 */
		req.setTemplateParamSet(templateParams);

		return this.send(req);
	}
	
	/*
	 * 填充请求参数，这里 request 对象的成员变量即对应接口的入参 您可以通过官网接口文档或跳转到 request 对象的定义处查看请求参数的定义
	 * 基本类型的设置: 帮助链接： 短信控制台：https://console.cloud.tencent.com/smsv2 sms
	 * helper：https://cloud.tencent.com/document/product/382/3773
	 */
	public SendSmsResponse send(SendSmsRequest req) throws TencentCloudSDKException {
		
		// 批量设置参数
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		
		/* 1、短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666 */
		map.from(smsProperties.getSdkappid()).to(req::setSmsSdkAppid);

		/* 2、短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息 */
		map.from(smsProperties.getSign()).to(req::setSign);
		
		/* 3、国际/港澳台短信 senderid: 国内短信填空，默认未开通，如需开通请联系 [sms helper] */
		map.from(smsProperties.getSenderId()).to(req::setSenderId);
		
		/* 4、短信码号扩展号: 默认未开通，如需开通请联系 [sms helper] */
		map.from(smsProperties.getEncode()).to(req::setExtendCode);
		
		/*
		 * 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的 返回的 res 是一个 SendSmsResponse
		 * 类的实例，与请求对象对应
		 */
		return client.SendSms(req);
	}

}
