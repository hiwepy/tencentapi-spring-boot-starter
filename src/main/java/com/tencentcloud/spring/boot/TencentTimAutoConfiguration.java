package com.tencentcloud.spring.boot;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tencentcloud.spring.boot.tim.TencentTimTemplate;
import com.tencentyun.TLSSigAPIv2;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

@Configuration
@ConditionalOnClass(TLSSigAPIv2.class)
@ConditionalOnProperty(prefix = TencentTimProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ TencentTimProperties.class })
public class TencentTimAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	public OkHttpClient okhttp3Client() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(6L, TimeUnit.SECONDS);
        builder.readTimeout(6L, TimeUnit.SECONDS);
        builder.writeTimeout(6L, TimeUnit.SECONDS);
        ConnectionPool connectionPool = new ConnectionPool(50, 60, TimeUnit.SECONDS);
        builder.connectionPool(connectionPool);
        return builder.build();
	}
	
	@Bean
	public TencentTimTemplate tencentTimTemplate(TencentTimProperties webimProperties,  OkHttpClient okhttp3Client) {
		// tencent-tim-java-sdk
		return new TencentTimTemplate(webimProperties, okhttp3Client);
	}

}
