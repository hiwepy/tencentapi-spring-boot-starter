package com.tencentcloud.spring.boot.tim.req.message;

import lombok.Data;

@Data
public class MsgDataBecomeFans extends MsgData {
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
}
