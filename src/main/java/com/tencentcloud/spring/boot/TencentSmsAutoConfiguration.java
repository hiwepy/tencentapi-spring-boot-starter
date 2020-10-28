package com.tencentcloud.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tencentcloud.spring.boot.sms.TencentSmsTemplate;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.sms.v20190711.SmsClient;

@Configuration
@ConditionalOnProperty(prefix = TencentSmsProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ TencentSmsProperties.class })
public class TencentSmsAutoConfiguration {
	
	/* 
	 * 1、实例化 SMS 的 client 对象 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量
	 */
	@Bean
	public SmsClient tencentSmsClient(TencentSmsProperties properties) {
		/*
		 * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
		 * 本示例采用从环境变量读取的方式，需要预先在环境变量中设置这两个值您也可以直接在代码中写入密钥对，但需谨防泄露，不要将代码复制、上传或者分享给他人 CAM
		 * 密钥查询：https://console.cloud.tencent.com/cam/capi
		 */
		Credential credential = new Credential(properties.getSecretId(), properties.getSecretKey());
		return new SmsClient(credential, properties.getRegion());
	}
	
	@Bean
	public TencentSmsTemplate tencentSmsTemplate(SmsClient smsClient, TencentSmsProperties properties) {
		return new TencentSmsTemplate(smsClient, properties);
	}

}