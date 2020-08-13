package com.tencentcloud.spring.boot.tim.req.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonArray;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MsgDataJoinOrLeaveRoom extends MsgData {
    /**
     * 用户ID
     */
    private Long senderId;
    /**
     * 用户昵称
     */
    private String senderName;
    /**
     * 用户等级
     */
    private Integer senderLevel;
    /**
     * 用户code
     */
    private String senderCode;
    /**
     * 是否新贵 0否 1是
     */
    private Integer senderIsUpstart;
    /**
     * 是否靓号 0否 1是
     */
    private Integer senderIsBeauty;

    /**
     * png名称
     */
    private String pngName;
    /**
     * 入场webp地址
     */
    private String webpUrl;
    /**
     * 热度值
     */
    private Integer hotValue;

    private JsonArray receiverIds;
}
