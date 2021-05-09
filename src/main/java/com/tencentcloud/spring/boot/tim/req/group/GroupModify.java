package com.tencentcloud.spring.boot.tim.req.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.req.common.MapKV;

import lombok.Data;

/**
 * 群组信息
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class GroupModify {

    /**
     * 需要修改基础信息的群组的 ID
     */
    @JsonProperty("GroupId")
    private String groupId;

    /**
     * 群名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
     */
    @JsonProperty("Name")
    private String groupName;

    /**
     * 群简介，最长240字节，使用 UTF-8 编码，1个汉字占3个字节
     */
    @JsonProperty("Introduction")
    private String introduction;

    /**
     * 群公告，最长300字节，使用 UTF-8 编码，1个汉字占3个字节
     */
    @JsonProperty("Notification")
    private String notification;

    /**
     * 群头像 URL，最长100字节
     */
    @JsonProperty("FaceUrl")
    private String faceUrl;

    /**
     * 最大群成员数量，缺省时的默认值：私有群是200，公开群是2000，聊天室是6000，音视频聊天室和在线成员广播大群无限制
     */
    @JsonProperty("MaxMemberCount")
    private Integer maxMemberCount;

    /**
     * 申请加群处理方式。包含 FreeAccess（自由加入），NeedPermission（需要验证），DisableApply（禁止加群），不填默认为 NeedPermission（需要验证）；仅当创建支持申请加群的 群组 时，该字段有效
     */
    @JsonProperty("ApplyJoinOption")
    private String applyJoinOption;

    /**
     * 群全员禁言状态。包含 On（开启），Off（关闭）
     */
    @JsonProperty("ShutUpAllMember")
    private String shutUpAllMember;
    
    /**
     * 群组维度的自定义字段（选填），默认情况是没有的，可以通过 即时通信 IM 控制台 进行配置，详情请参阅 自定义字段
     */
    @JsonProperty("AppDefinedData")
    private List<MapKV> AppDefinedData;

}
