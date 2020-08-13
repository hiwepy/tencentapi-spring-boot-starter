package com.tencentcloud.spring.boot.tim.req.message;

import lombok.Data;

@Data
public class MsgDataRedEnvelope extends MsgData{
    /**
     * 房间id
     */
    private Long roomId;
    /**
     * 发红包人昵称
     */
    private String senderName;
    /**
     * 房主昵称
     */
    private String anchorName;
    /**
     * 红包总金额
     */
    private Integer redEnvelopeGold;
}
