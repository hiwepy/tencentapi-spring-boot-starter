package com.tencentcloud.spring.boot.tim.req.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgData {
    protected Long roomId;
    /**
     * 消息内容
     */
    protected Map<String,String> message;
    protected Long receiverId;
}
