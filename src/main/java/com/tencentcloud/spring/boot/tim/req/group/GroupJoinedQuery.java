package com.tencentcloud.spring.boot.tim.req.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class GroupJoinedQuery {

    /**
     * 需要查询的用户帐号
     */
    @JsonProperty("Member_Account")
    private String account;

    /**
     * 是否获取用户加入的 AVChatRoom(直播群)，0表示不获取，1表示获取。默认为0
     */
    @JsonProperty("WithHugeGroups")
    private Integer withHugeGroups = 0;
    
    /**
     * 是否获取用户已加入但未激活的 Private（即新版本中 Work，好友工作群) 群信息，0表示不获取，1表示获取。默认为0
     */
    @JsonProperty("WithNoActiveGroups")
    private Integer withNoActiveGroups = 0;
    
    /**
     * 单次拉取的群组数量，如果不填代表所有群组
     */
    @JsonProperty("Limit")
    private Integer limit;

    /**
     * 从第多少个群组开始拉取
     */
    @JsonProperty("Offset")
    private Integer offset;
    
    /**
     * 分别包含 GroupBaseInfoFilter 和 SelfInfoFilter 两个过滤器； 
     * GroupBaseInfoFilter 表示需要拉取哪些基础信息字段，详情请参阅 群组系统；
     * SelfInfoFilter 表示需要拉取用户在每个群组中的哪些个人资料，详情请参阅 群组系统
     */
    @JsonProperty("ResponseFilter")
    private GroupJoinedResponseFilter responseFilter;
    
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public class GroupJoinedResponseFilter {
	
	    /**
	     * 基础信息字段过滤器，指定需要获取的基础信息字段，基础信息字段详情请参阅 群基础资料
	     */
	    @JsonProperty("GroupBaseInfoFilter")
	    private List<String> baseInfoFilter;
	
	    /**
	     * SelfInfoFilter 表示需要拉取用户在每个群组中的哪些个人资料
	     */
	    @JsonProperty("SelfInfoFilter")
	    private List<String> selfInfoFilter;
	
	}
    
}
