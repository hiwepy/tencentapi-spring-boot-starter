package com.tencentcloud.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tencentcloud.spring.boot.trtc.TencentTrtcTemplate;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.trtc.v20190722.TrtcClient;

@Configuration
@ConditionalOnClass(TrtcClient.class)
@ConditionalOnProperty(prefix = TencentTrtcProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ TencentTrtcProperties.class })
public class TencentTrtcAutoConfiguration {

	/*
	 * 1、实例化 Trtc 的 client 对象 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量
	 */
	@Bean
	public TrtcClient tencentTrtcClient(TencentTrtcProperties properties) {
		
		/*
		 * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
		 * 密钥查询：https://console.cloud.tencent.com/cam/capi
		 */
		Credential credential = new Credential(properties.getSecretId(), properties.getSecretKey());

        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(properties.getSignMethod()); // 指定签名算法(默认为HmacSHA256)
        // 自3.1.80版本开始，SDK 支持打印日志。
        clientProfile.setHttpProfile(properties.getHttpProfile());
        clientProfile.setDebug(properties.isDebug());
        // 从3.1.16版本开始，支持设置公共参数 Language, 默认不传，选择(ZH_CN or EN_US)
        clientProfile.setLanguage(properties.getLanguage());
        
		return new TrtcClient(credential, properties.getRegion(), clientProfile);
	}

	@Bean
	public TencentTrtcTemplate tencentTrtcTemplate(TrtcClient trtcClient, TencentTrtcProperties properties) {
		return new TencentTrtcTemplate(trtcClient, properties);
	}

}