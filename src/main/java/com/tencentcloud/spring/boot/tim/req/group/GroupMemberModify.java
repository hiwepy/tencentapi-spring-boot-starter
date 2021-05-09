package com.tencentcloud.spring.boot.tim.req.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.req.common.MapKV;

import lombok.Data;


@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class GroupMemberModify {

    /**
     * 要操作的群组（必填）
     */
    @JsonProperty("GroupId")
    private String groupId;
    
    /**
     * 要操作的群成员（必填）
     */
    @JsonProperty("Member_Account")
    private String account;

    /**
     * 成员身份，Admin/Member 分别为设置/取消管理员
     */
    @JsonProperty("Role")
    private String role;
	
    /**
	 * 消息屏蔽选项
	 */
	@JsonProperty("MsgFlag")
	private String msgFlag;

    /**
	 * 群名片（最大不超过50个字节）
	 */
	@JsonProperty("NameCard")
	private String nameCard;
	
    /**
	 * 需禁言时间，单位为秒，0表示取消禁言
	 */
	@JsonProperty("ShutUpTime")
	private Long shutUpTime;

    /**
     * 群成员维度的自定义字段，默认情况是没有的，可以通过 即时通信 IM 控制台 进行配置，详情请参阅 自定义字段
     */
    @JsonProperty("AppMemberDefinedData")
    private List<MapKV> appMemberDefinedData;
    
}