package com.tencentcloud.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tencentyun.TLSSigAPIv2;

@Configuration
@ConditionalOnProperty(prefix = TencentCloudProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ TencentCloudProperties.class })
public class TencentCloudAutoConfiguration {

	@Bean
	public TLSSigAPIv2 tlsSigAPIv2(TencentCloudProperties properties) {
		return new TLSSigAPIv2(properties.getSdkappid(), properties.getKey());
	}

}
