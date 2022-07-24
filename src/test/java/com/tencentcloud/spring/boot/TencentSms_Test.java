package com.tencentcloud.spring.boot;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencentcloud.spring.boot.sms.TencentSmsTemplate;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TencentSms_Test {

	ObjectMapper objectMapper = new ObjectMapper();
	SmsClient tencentSmsClient;
	TencentSmsProperties properties = new TencentSmsProperties();

	@BeforeAll
	public void setup() {
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		/*
		 * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
		 * 本示例采用从环境变量读取的方式，需要预先在环境变量中设置这两个值您也可以直接在代码中写入密钥对，但需谨防泄露，不要将代码复制、上传或者分享给他人 CAM
		 * 密钥查询：https://console.cloud.tencent.com/cam/capi
		 */
		Credential credential = new Credential(properties.getSecretId(), properties.getSecretKey());
		tencentSmsClient = new SmsClient(credential, properties.getRegion());

	}

	@Test
	public void testSendSms() {

		try {
			TencentSmsTemplate template = new TencentSmsTemplate(tencentSmsClient, properties);
			SendSmsResponse response = template.send("13733339999", "9781232", "你好");
			System.out.println(objectMapper.writeValueAsString(response));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
