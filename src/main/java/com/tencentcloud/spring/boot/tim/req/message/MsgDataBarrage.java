package com.tencentcloud.spring.boot.tim.req.message;

import lombok.Data;

/**
 *  弹幕
 */
@Data
public class MsgDataBarrage extends MsgData{
    /**
     * 发送者id
     */
    private Long senderId;
    /**
     * 发送者昵称
     */
    private String senderName;
    /**
     * 发送者头像
     */
    private String senderAvatar;
    /**
     * - 发送人等级
     */
    private Integer senderLevel;
    /**
     * 弹幕内容
     */
    private String content;
}
