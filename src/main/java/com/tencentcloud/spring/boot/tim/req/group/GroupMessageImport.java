package com.tencentcloud.spring.boot.tim.req.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 群组消息导入实体
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GroupMessageImport {

	 /**
     * 待导入的群成员帐号
     */
    @JsonProperty("Member_Account")
    private String account;

    /**
     * 待导入群成员角色；目前只支持填 Admin，不填则为普通成员 Member
     */
    @JsonProperty("Role")
    private String role;

    /**
     * 待导入群成员的入群时间（UTC 时间）
     */
    @JsonProperty("JoinTime")
    private Integer joinTime;
	 
    /**
     * 待导入群成员的未读消息计数
     */
    @JsonProperty("UnreadMsgNum")
    private Integer unreadMsgNum;
}
