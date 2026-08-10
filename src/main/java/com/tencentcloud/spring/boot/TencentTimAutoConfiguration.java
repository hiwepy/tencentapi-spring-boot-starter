package com.tencentcloud.spring.boot;

import com.tencentcloud.spring.boot.tim.TencentTimOption;
import com.tencentcloud.spring.boot.tim.TencentTimTemplate;
import com.tencentcloud.spring.boot.tim.TimInfoProvider;
import com.tencentyun.TLSSigAPIv2;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot auto-configuration for the Tencent Cloud IM (TIM) integration.
 * <p>
 * Activates only when the {@code TLSSigAPIv2} class is on the classpath and
 * {@code tencent.cloud.tim.enabled=true}. It exposes a
 * {@link TencentTimTemplate} facade bean, sharing an {@link OkHttpClient} and
 * a {@link TimInfoProvider} when available, falling back to plain defaults
 * otherwise.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(TLSSigAPIv2.class)
@ConditionalOnProperty(prefix = TencentTimProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ TencentCloudProperties.class, TencentTimProperties.class })
public class TencentTimAutoConfiguration {

	/**
	 * Creates the {@link TencentTimTemplate} facade used by application code to
	 * call TIM REST APIs and generate user signatures.
	 * <p>
	 * A shared {@link OkHttpClient} is reused when one is registered in the
	 * context; otherwise a default client is created. When no
	 * {@link TimInfoProvider} bean is present a fallback provider that returns
	 * the bound {@link TencentTimProperties} for any SdkAppId is used.</p>
	 *
	 * @param timProperties        bound TIM configuration
	 * @param okhttp3ClientProvider object provider for the optional shared HTTP client
	 * @param timUserIdProvider    object provider for the optional TIM info provider
	 * @return a configured {@link TencentTimTemplate}
	 */
	@Bean
	public TencentTimTemplate tencentTimTemplate(
			TencentTimProperties timProperties,
			ObjectProvider<OkHttpClient> okhttp3ClientProvider,
			ObjectProvider<TimInfoProvider> timUserIdProvider) {
		OkHttpClient okhttp3Client = okhttp3ClientProvider.getIfAvailable(() -> new OkHttpClient.Builder().build());
		return new TencentTimTemplate(timProperties, okhttp3Client, timUserIdProvider.getIfAvailable(() -> sdkAppId -> timProperties));
	}

}
