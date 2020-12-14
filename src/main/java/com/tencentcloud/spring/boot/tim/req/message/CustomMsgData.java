package com.tencentcloud.spring.boot.tim.req.message;

import lombok.Data;

@Data
public class CustomMsgData {
	private Integer type;
	private MsgData data;
}
