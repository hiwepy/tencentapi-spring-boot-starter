package com.tencentcloud.spring.boot.tim.resp.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 群组信息
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class GroupInfoResponse {

    /**
     * appid
     */
    @JsonProperty("Appid")
    private Integer appid;

    /**
     * 申请加群选项
     */
    @JsonProperty("ApplyJoinOption")
    private String applyJoinOption;

    /**
     * 创建时间
     */
    @JsonProperty("CreateTime")
    private Integer createTime;


    /**
     * 错误code
     */
    @JsonProperty("ErrorCode")
    private String errorCode;

    /**
     * 群头像
     */
    @JsonProperty("FaceUrl")
    private String faceUrl;

    /**
     * 群组iD
     */
    @JsonProperty("GroupId")
    private String groupId;

    /**
     * 群组简介
     */
    @JsonProperty("Introduction")
    private String introduction;

    /**
     * 最后群资料变更时间（UTC 时间）
     */
    @JsonProperty("LastInfoTime")
    private Integer lastInfoTime;

    /**
     * 群内最后一条消息的时间（UTC 时间）
     */
    @JsonProperty("LastMsgTime")
    private Integer lastMsgTime;

    /**
     * 最大成员数
     */
    @JsonProperty("MaxMemberNum")
    private Integer maxMemberNum;

    /**
     * 当前成员数
     */
    @JsonProperty("MemberNum")
    private Integer memberNum;

    /**
     * 群名称
     */
    @JsonProperty("Name")
    private String name;

    /**
     * 群内下一条消息的 Seq
     */
    @JsonProperty("NextMsgSeq")
    private Integer nextMsgSeq;

    /**
     * 群组公告
     */
    @JsonProperty("Notification")
    private String notification;

    /**
     * 在线成员数量
     */
    @JsonProperty("OnlineMemberNum")
    private String onlineMemberNum;

    /**
     * 群组ID
     */
    @JsonProperty("Owner_Account")
    private String ownerAccount;

    /**
     * 群全员禁言状态
     */
    @JsonProperty("ShutUpAllMember")
    private String shutUpAllMember;

    /**
     * 群组类型
     */
    @JsonProperty("Type")
    private String type;

    /**
     * 群成员列表
     */
    @JsonProperty("MemberList")
    private List<MemberResponse> memberList;

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
    
}
