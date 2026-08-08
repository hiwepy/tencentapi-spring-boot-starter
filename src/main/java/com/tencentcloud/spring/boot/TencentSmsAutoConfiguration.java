package com.tencentcloud.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.tencentcloud.spring.boot.sms.TencentSmsTemplate;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;

/**
 * Spring Boot auto-configuration for the Tencent Cloud SMS integration.
 * <p>
 * Activates only when both the {@code SmsClient} class is on the classpath and
 * {@code tencent.cloud.sms.enabled=true}. It builds the SDK {@link SmsClient}
 * (using the per-service credentials with fallback to the shared
 * {@link TencentCloudProperties}) and exposes a
 * {@link TencentSmsTemplate} facade bean for application code.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(SmsClient.class)
@ConditionalOnProperty(prefix = TencentSmsProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ TencentCloudProperties.class, TencentSmsProperties.class })
public class TencentSmsAutoConfiguration {

	/**
	 * Creates the Tencent Cloud SMS SDK client.
	 * <p>
	 * Credentials are resolved with per-service override first, falling back to
	 * the shared {@link TencentCloudProperties} values. The {@link ClientProfile}
	 * is configured with the signature method, HTTP profile, debug flag and
	 * response language from the bound properties.
	 *
	 * @param cloudProperties shared Tencent Cloud credentials and global flags
	 * @param smsProperties   SMS-specific configuration (region, profile, signing)
	 * @return a configured {@link SmsClient} bound to the requested region
	 */
	@Bean
	public SmsClient tencentSmsClient(TencentCloudProperties cloudProperties,TencentSmsProperties smsProperties) {
		String secretId = StringUtils.hasText(smsProperties.getSecretId()) ? smsProperties.getSecretId() : cloudProperties.getSecretId();
		String secretKey = StringUtils.hasText(smsProperties.getSecretKey()) ? smsProperties.getSecretKey() : cloudProperties.getSecretKey();
		Credential credential = new Credential(secretId, secretKey);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(smsProperties.getSignMethod());
        clientProfile.setHttpProfile(smsProperties.getHttpProfile());
        clientProfile.setDebug(cloudProperties.isDebug());
        clientProfile.setLanguage(smsProperties.getLanguage());

		return new SmsClient(credential, smsProperties.getRegion(), clientProfile);
	}

	/**
	 * Creates the {@link TencentSmsTemplate} facade used by application code to
	 * send SMS messages.
	 *
	 * @param smsClient   the {@link SmsClient} created above
	 * @param properties  SMS-specific configuration
	 * @return a {@link TencentSmsTemplate} wrapping the client and properties
	 */
	@Bean
	@ConditionalOnBean
	public TencentSmsTemplate tencentSmsTemplate(SmsClient smsClient, TencentSmsProperties properties) {
		return new TencentSmsTemplate(smsClient, properties);
	}

}