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

/**
 * Spring Boot auto-configuration for the Tencent Cloud Live (LVB) integration.
 * <p>
 * Activates only when both the {@code LiveClient} class is on the classpath and
 * {@code tencent.cloud.live.enabled=true}. It builds the SDK {@link LiveClient}
 * (using the per-service credentials with fallback to the shared
 * {@link TencentCloudProperties}) and exposes a
 * {@link TencentLiveTemplate} facade bean for application code.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(LiveClient.class)
@ConditionalOnProperty(prefix = TencentLiveProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ TencentCloudProperties.class, TencentLiveProperties.class })
public class TencentLiveAutoConfiguration {

	/**
	 * Creates the Tencent Cloud Live SDK client.
	 * <p>
	 * Credentials are resolved with per-service override first, falling back to
	 * the shared {@link TencentCloudProperties} values. The {@link ClientProfile}
	 * is configured with the signature method, HTTP profile, debug flag and
	 * response language from the bound properties.
	 *
	 * @param cloudProperties shared Tencent Cloud credentials and global flags
	 * @param liveProperties  Live-specific configuration (region, profile, signing)
	 * @return a configured {@link LiveClient} bound to the requested region
	 */
	@Bean
	public LiveClient tencentLiveClient(TencentCloudProperties cloudProperties, TencentLiveProperties liveProperties) {

		String secretId = StringUtils.hasText(liveProperties.getSecretId()) ? liveProperties.getSecretId() : cloudProperties.getSecretId();
		String secretKey = StringUtils.hasText(liveProperties.getSecretKey()) ? liveProperties.getSecretKey() : cloudProperties.getSecretKey();
		Credential credential = new Credential(secretId, secretKey);

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(liveProperties.getSignMethod());
        clientProfile.setHttpProfile(liveProperties.getHttpProfile());
        clientProfile.setDebug(cloudProperties.isDebug());
        clientProfile.setLanguage(liveProperties.getLanguage());

		return new LiveClient(credential, liveProperties.getRegion(), clientProfile);
	}

	/**
	 * Creates the {@link TencentLiveTemplate} facade used by application code to
	 * invoke Live operations.
	 *
	 * @param liveClient  the {@link LiveClient} created above
	 * @param properties  Live-specific configuration
	 * @return a {@link TencentLiveTemplate} wrapping the client and properties
	 */
	@Bean
	@ConditionalOnBean
	public TencentLiveTemplate tencentLiveTemplate(LiveClient liveClient, TencentLiveProperties properties) {
		return new TencentLiveTemplate(liveClient, properties);
	}

}