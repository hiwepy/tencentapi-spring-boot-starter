package com.tencentcloud.spring.boot;

import com.tencentcloud.spring.boot.tim.TencentTimTemplate;

public class Test {

	public static void main(String[] args) {
		TencentTimTemplate timTemplate = null;
		timTemplate.opsForAccount().delete(new String[] {"111"});
		timTemplate.opsForProfile().portraitGet("111");
		
	}
	
}
