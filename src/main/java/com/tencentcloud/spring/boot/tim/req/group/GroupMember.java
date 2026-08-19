package com.tencentcloud.spring.boot.tim.req.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.req.common.MapKV;

import lombok.Data;


/**
 * <p>Group Member.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class GroupMember {

    /**
     * 成员（必填）
     */
    @JsonProperty("Member_Account")
    private String memberAccount;

    /**
     * 赋予该成员的身份，目前备选项只有Admin（选填）
     */
    @JsonProperty("Role")
    private String role;

    /**
     * 入群时间（UTC 时间）
     */
    @JsonProperty("JoinTime")
    private Long joinTime;
    
    /**
	 * 消息的序列值
	 */
	@JsonProperty("MsgSeq")
	private Long msgSeq;
	
    /**
	 * 消息屏蔽选项
	 */
	@JsonProperty("MsgFlag")
	private String msgFlag;
	
    /**
	 * 最后发言时间（UTC 时间）
	 */
	@JsonProperty("LastSendMsgTime")
	private Long lastSendMsgTime;

    /**
	 * 禁言截止时间（UTC 时间）
	 */
	@JsonProperty("ShutUpUntil")
	private Long shutUpUntil;

    /**
     * 群成员维度的自定义字段，默认情况是没有的，可以通过 即时通信 IM 控制台 进行配置，详情请参阅 自定义字段
     */
    @JsonProperty("AppMemberDefinedData")
    private List<MapKV> appMemberDefinedData;
    
}