package com.tencentcloud.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.tencentcloud.spring.boot.live.TencentLiveTemplate;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.live.v20180801.LiveClient;

@Configuration
@ConditionalOnClass(LiveClient.class)
@ConditionalOnProperty(prefix = TencentLiveProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ TencentCloudProperties.class, TencentLiveProperties.class })
public class TencentLiveAutoConfiguration {
	
	/* 
	 * 1、实例化 Live 的 client 对象 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量
	 */
	@Bean
	public LiveClient tencentLiveClient(TencentCloudProperties cloudProperties, TencentLiveProperties liveProperties) {
		
		/*
		 * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
		 * 密钥查询：https://console.cloud.tencent.com/cam/capi
		 */
		String secretId = StringUtils.hasText(liveProperties.getSecretId()) ? liveProperties.getSecretId() : cloudProperties.getSecretId();
		String secretKey = StringUtils.hasText(liveProperties.getSecretKey()) ? liveProperties.getSecretKey() : cloudProperties.getSecretKey();
		Credential credential = new Credential(secretId, secretKey);
		
		// 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(liveProperties.getSignMethod()); // 指定签名算法(默认为HmacSHA256)
        // 自3.1.80版本开始，SDK 支持打印日志。
        clientProfile.setHttpProfile(liveProperties.getHttpProfile());
        clientProfile.setDebug(cloudProperties.isDebug());
        // 从3.1.16版本开始，支持设置公共参数 Language, 默认不传，选择(ZH_CN or EN_US)
        clientProfile.setLanguage(liveProperties.getLanguage());
		
		return new LiveClient(credential, liveProperties.getRegion(), clientProfile);
	}
	
	@Bean
	@ConditionalOnBean
	public TencentLiveTemplate tencentLiveTemplate(LiveClient liveClient, TencentLiveProperties properties) {
		return new TencentLiveTemplate(liveClient, properties);
	}

}