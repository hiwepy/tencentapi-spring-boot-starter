package com.tencentcloud.spring.boot;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tencentcloud.spring.boot.tim.TencentTimTemplate;
import com.tencentcloud.spring.boot.tim.TimUserIdProvider;
import com.tencentyun.TLSSigAPIv2;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

@Configuration
@ConditionalOnClass(TLSSigAPIv2.class)
@ConditionalOnProperty(prefix = TencentTimProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ TencentTimProperties.class, TencentOkHttp3Properties.class })
public class TencentTimAutoConfiguration {
	
	
	
	@Bean
	public TencentTimTemplate tencentTimTemplate(
			TencentTimProperties timProperties,
			TencentOkHttp3Properties okHttp3Properties,
			ObjectProvider<OkHttpClient> okhttp3ClientProvider,
			ObjectProvider<TimUserIdProvider> timUserIdProvider) {
		
		OkHttpClient okhttp3Client = okhttp3ClientProvider.getIfAvailable(() -> { 
			
			OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.callTimeout(okHttp3Properties.getCallTimeout(), TimeUnit.SECONDS)
				.connectionPool(new ConnectionPool(okHttp3Properties.getMaxIdleConnections(),
						okHttp3Properties.getKeepAliveDuration().getSeconds(), TimeUnit.SECONDS))
				.connectTimeout(okHttp3Properties.getConnectTimeout(), TimeUnit.SECONDS)
				.followRedirects(okHttp3Properties.isFollowRedirects())
				.followSslRedirects(okHttp3Properties.isFollowSslRedirects())
				.pingInterval(okHttp3Properties.getPingInterval(), TimeUnit.SECONDS)
				.readTimeout(okHttp3Properties.getReadTimeout(), TimeUnit.SECONDS)
				.retryOnConnectionFailure(okHttp3Properties.isRetryOnConnectionFailure())
				.writeTimeout(okHttp3Properties.getWriteTimeout(), TimeUnit.SECONDS);
				
	        return builder.build();
	        
		});
		
		return new TencentTimTemplate(timProperties, okhttp3Client, timUserIdProvider.getIfAvailable(() -> {
			return new TimUserIdProvider() {};
		}));
	}

}
