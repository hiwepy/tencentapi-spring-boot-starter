package com.tencentcloud.spring.boot;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.tencentcloud.spring.boot.trtc.TencentTrtcTemplate;
import com.tencentcloud.spring.boot.trtc.TrtcUserIdProvider;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.trtc.v20190722.TrtcClient;

/**
 * Spring Boot auto-configuration for the Tencent Real-Time Communication (TRTC)
 * integration.
 * <p>
 * Activates only when both the {@code TrtcClient} class is on the classpath and
 * {@code tencent.cloud.trtc.enabled=true}. It builds the SDK {@link TrtcClient}
 * (using the per-service credentials with fallback to the shared
 * {@link TencentCloudProperties}) and exposes a
 * {@link TencentTrtcTemplate} facade bean for application code.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(TrtcClient.class)
@ConditionalOnProperty(prefix = TencentTrtcProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ TencentCloudProperties.class, TencentTrtcProperties.class })
public class TencentTrtcAutoConfiguration {

	/**
	 * Creates the Tencent Cloud TRTC SDK client.
	 * <p>
	 * Credentials are resolved with per-service override first, falling back to
	 * the shared {@link TencentCloudProperties} values. The {@link ClientProfile}
	 * is configured with the signature method, HTTP profile, debug flag and
	 * response language from the bound properties.
	 *
	 * @param cloudProperties shared Tencent Cloud credentials and global flags
	 * @param trtcProperties  TRTC-specific configuration (region, profile, signing)
	 * @return a configured {@link TrtcClient} bound to the requested region
	 */
	@Bean
	public TrtcClient trtcClient(TencentCloudProperties cloudProperties, TencentTrtcProperties trtcProperties) {

		String secretId = StringUtils.hasText(trtcProperties.getSecretId()) ? trtcProperties.getSecretId() : cloudProperties.getSecretId();
		String secretKey = StringUtils.hasText(trtcProperties.getSecretKey()) ? trtcProperties.getSecretKey() : cloudProperties.getSecretKey();
		Credential credential = new Credential(secretId, secretKey);

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(trtcProperties.getSignMethod());
        clientProfile.setHttpProfile(trtcProperties.getHttpProfile());
        clientProfile.setDebug(cloudProperties.isDebug());
        clientProfile.setLanguage(trtcProperties.getLanguage());

		return new TrtcClient(credential, trtcProperties.getRegion(), clientProfile);
	}

	/**
	 * Creates the {@link TencentTrtcTemplate} facade used by application code to
	 * manage TRTC rooms and users.
	 *
	 * @param trtcClient         the {@link TrtcClient} created above
	 * @param properties         TRTC-specific configuration
	 * @param trtcUserIdProvider object provider for the optional user-id resolver
	 * @return a {@link TencentTrtcTemplate} wrapping the client, properties and provider
	 */
	@Bean
	@ConditionalOnBean
	public TencentTrtcTemplate tencentTrtcTemplate(TrtcClient trtcClient, TencentTrtcProperties properties,
			ObjectProvider<TrtcUserIdProvider> trtcUserIdProvider) {
		return new TencentTrtcTemplate(trtcClient, properties, trtcUserIdProvider.getIfAvailable(() -> {
			return new TrtcUserIdProvider() {};
		}));
	}

}