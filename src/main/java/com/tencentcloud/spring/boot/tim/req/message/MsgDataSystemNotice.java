package com.tencentcloud.spring.boot.tim.req.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class MsgDataSystemNotice extends MsgData {
    /**
     * 发送人的ID
     */
    private Long senderId;
    /**
     *  - 发送人的昵称
     */
    private String senderName;

    /**
     *  - 游戏名称
     */
    private String gameName;
    /**
     *  - 礼物名称
     */
    private String giftName;
    /**
     * 返奖倍数
     */
    private Integer multiple;
    /**
     * 奖励数量
     */
    private Integer coin;

}
