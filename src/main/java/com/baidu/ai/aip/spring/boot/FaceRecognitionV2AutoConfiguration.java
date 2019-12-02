package com.baidu.ai.aip.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = FaceRecognitionV2Properties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ FaceRecognitionV2Properties.class })
public class FaceRecognitionV2AutoConfiguration {
	
	@Bean
	public FaceRecognitionV2Template faceRecognitionV2Template(FaceRecognitionV2Properties properties) {
		return new FaceRecognitionV2Template(properties);
	}
	
	
}
