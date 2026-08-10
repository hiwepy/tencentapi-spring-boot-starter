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
package com.tencentcloud.spring.boot.sms;

import org.springframework.boot.context.properties.PropertyMapper;

import com.tencentcloud.spring.boot.TencentSmsProperties;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

/**
 * Facade around the Tencent Cloud SMS SDK {@link SmsClient} that simplifies
 * sending text messages using the bound {@link TencentSmsProperties}
 * (signature, SdkAppid, sender id, etc.).
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class TencentSmsTemplate {

	private final SmsClient smsClient;
	private final TencentSmsProperties smsProperties;

	/**
	 * Wraps the given SDK client and configuration.
	 *
	 * @param smsClient      the Tencent SMS SDK client
	 * @param smsProperties  the bound SMS configuration
	 */
	public TencentSmsTemplate(SmsClient smsClient, TencentSmsProperties smsProperties) {
		this.smsClient = smsClient;
		this.smsProperties = smsProperties;
	}

	/**
	 * Sends an SMS to a single phone number using the given approved template.
	 *
	 * @param phoneNumber    destination phone number in E.164 format (e.g. {@code +8613711112222})
	 * @param templateID     approved template id from the SMS console
	 * @param templateParams template parameter values, in declared order (may be empty)
	 * @return the SDK response carrying the send status
	 * @throws TencentCloudSDKException if the SDK call fails
	 */
	public SendSmsResponse send(String phoneNumber, String templateID, String... templateParams) throws TencentCloudSDKException {
		return this.send(new String[] {phoneNumber}, templateID, templateParams);
	}

	/**
	 * Sends an SMS to up to 200 phone numbers using the given approved template.
	 *
	 * @param phoneNumbers   destination phone numbers in E.164 format (max 200)
	 * @param templateID     approved template id from the SMS console
	 * @param templateParams template parameter values, in declared order (may be empty)
	 * @return the SDK response carrying the send status
	 * @throws TencentCloudSDKException if the SDK call fails
	 */
	public SendSmsResponse send(String[] phoneNumbers, String templateID, String... templateParams) throws TencentCloudSDKException {

		SendSmsRequest req = new SendSmsRequest();
		req.setTemplateID(templateID);
		req.setPhoneNumberSet(phoneNumbers);
		req.setTemplateParamSet(templateParams);

		return this.send(req);
	}

	/**
	 * Sends an SMS using a fully-customised request, filling the SdkAppid,
	 * signature, sender id, session context and extension code from the bound
	 * configuration when those values are present.
	 *
	 * @param req the SDK request to enrich and send
	 * @return the SDK response carrying the send status
	 * @throws TencentCloudSDKException if the SDK call fails
	 */
	public SendSmsResponse send(SendSmsRequest req) throws TencentCloudSDKException {

		// Apply all non-null bound properties onto the request.
		PropertyMapper map = PropertyMapper.get();

		map.from(smsProperties.getSdkappid()).to(req::setSmsSdkAppid);
		map.from(smsProperties.getSign()).to(req::setSign);
		map.from(smsProperties.getSenderId()).to(req::setSenderId);
		map.from(smsProperties.getSession()).to(req::setSessionContext);
		map.from(smsProperties.getEncode()).to(req::setExtendCode);

		return smsClient.SendSms(req);
	}

	/**
	 * @return the underlying Tencent SMS SDK client.
	 */
	public SmsClient getSmsClient() {
		return smsClient;
	}

	/**
	 * @return the bound SMS configuration.
	 */
	public TencentSmsProperties getSmsProperties() {
		return smsProperties;
	}

}
