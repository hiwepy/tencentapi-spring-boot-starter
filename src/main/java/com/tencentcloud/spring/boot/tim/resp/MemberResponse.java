package com.tencentcloud.spring.boot.tim.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author : cw
 * @create : 2018 - 07 - 11
 * 成员信息
 */
@Data
@JsonInclude( JsonInclude.Include.NON_NULL)
public class MemberResponse {

    /**
     * 入群时间（UTC时间）
     */
    @JsonProperty("JoinTime")
    private Integer joinTime;
    /**
     *  最后发言时间（UTC时间）
     */
    @JsonProperty("LastSendMsgTime")
    private Integer lastSendMsgTime;

    /**
     * 成员ID
     */
    @JsonProperty("Member_Account")
    private String memberAccount;

    /**
     * 消息屏蔽选项
     */
    @JsonProperty("MsgFlag")
    private String msgFlag;

    /**
     * 1233
     */
    @JsonProperty("MsgSeq")
    private Integer msgSeq;

    /**
     * 群内角色
     */
    @JsonProperty("Role")
    private String role;

    /**
     * 禁言截至时间（UTC时间）
     */
    @JsonProperty("ShutUpUntil")
    private Integer shutUpUntil;

    /**
     *加人结果：0 为失败；1 为成功；2 为已经是群成员
     * @return
     */
    @JsonProperty("Result")
    private Integer result;

}
