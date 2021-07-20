package com.tencentcloud.spring.boot.tim.resp.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.req.common.MapKV;
import com.tencentcloud.spring.boot.tim.req.group.GroupMember;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupInfo {

    /**
     * 即时通信应用的 SDKAppID
     */
    @JsonProperty("Appid")
    private String appId;
	/**
	 * 群组ID，由即时通信 IM 后台分配
	 */
	@JsonProperty("GroupId")
	private String groupId;
	/**
	 * 针对该群组的返回结果
	 */
	@JsonProperty("ErrorCode")
	private String errorCode;
	/**
	 * 针对该群组的返回结果
	 */
	@JsonProperty("ErrorInfo")
	private String errorInfo;
    /**
     * 群组类型；包括 Public（公开群），Private（私密群），
     * ChatRoom（聊天室），AVChatRoom（互动直播聊天室），
     * BChatRoom（在线成员广播大群）
     */
    @JsonProperty("Type")
    private String type;
    /**
     * 群组名称，最长30字节，使用 UTF-8 编码，1个汉字占3个字节
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
     * 群主 id，自动添加到群成员中。如果不填，群没有群主。
     */
    @JsonProperty("Owner_Account")
    private String ownerAccount;
    
	/**
	 *  群组创建时间（UTC 时间）
	 */
	@JsonProperty("CreateTime")
	private Long createTime;
	/**
	 * 最后群资料变更时间（UTC 时间）
	 */
	@JsonProperty("LastInfoTime")
	private Long lastInfoTime;
	/**
	 * 群内最后一条消息的时间（UTC 时间）
	 */
	@JsonProperty("LastMsgTime")
	private Long lastMsgTime;
	/**
	 * 下一条消息的序列值
	 */
	@JsonProperty("NextMsgSeq")
	private Long nextMsgSeq;
	/**
	 * 当前群成员数量
	 */
	@JsonProperty("MemberNum")
	private Long memberNum;
	/**
	 * 最大群成员数量
	 */
	@JsonProperty("MaxMemberNum")
	private Long maxMemberNum;

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

    /**
     * 初始群成员列表，最多500个；成员信息字段详情请参阅 群成员资料
     */
    @JsonProperty("MemberList")
    private List<GroupMember> memberList;
	
}
