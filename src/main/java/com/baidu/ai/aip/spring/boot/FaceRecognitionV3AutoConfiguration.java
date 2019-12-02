package com.baidu.ai.aip.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = FaceRecognitionV3Properties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ FaceRecognitionV3Properties.class })
public class FaceRecognitionV3AutoConfiguration {
	
	@Bean
	public FaceRecognitionV3Template faceRecognitionV3Template(FaceRecognitionV3Properties properties) {
		return new FaceRecognitionV3Template(properties);
	}
	
}
