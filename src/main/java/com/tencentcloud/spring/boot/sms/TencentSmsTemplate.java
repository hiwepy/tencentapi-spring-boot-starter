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

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

public class TencentSmsTemplate {

	private SmsClient client;
	
	public TencentSmsTemplate(SmsClient client) {
		this.client = client;
	}

	public SendSmsResponse send(SendSmsRequest req) throws TencentCloudSDKException {
		/*
		 * 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的 返回的 res 是一个 SendSmsResponse
		 * 类的实例，与请求对象对应
		 */
		SendSmsResponse res = client.SendSms(req);
		if (!res.getSendStatusSet()[0].getCode().equals("Ok")) {
			throw new TencentCloudSDKException(res.getSendStatusSet()[0].getMessage());
		}
		return res;
	}

}
