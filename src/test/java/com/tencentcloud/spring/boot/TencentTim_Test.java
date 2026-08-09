package com.tencentcloud.spring.boot;

import com.tencentcloud.spring.boot.tim.TencentTimOption;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencentcloud.spring.boot.tim.TencentTimTemplate;
import com.tencentcloud.spring.boot.tim.TimInfoProvider;

import okhttp3.OkHttpClient;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TencentTim_Test {

	ObjectMapper objectMapper = new ObjectMapper();
	OkHttpClient okhttp3Client = new OkHttpClient.Builder().build();
	TencentTimProperties properties = new TencentTimProperties();

	@Before
	public void setup() {
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		properties.setSdkappid(1400288577L);
	}

	@Test
	public void testTemplateCreation() {
		TencentTimTemplate template = new TencentTimTemplate(properties, okhttp3Client, new TimInfoProvider() {
			@Override
			public TencentTimOption getTimOptionBySdkAppId(Long sdkAppId) {
				return null;
			}
		});
		assertNotNull(template);
	}

	@Test
	public void testPropertiesDefaults() {
		TencentTimProperties props = new TencentTimProperties();
		assertNotNull(props);
	}

}
