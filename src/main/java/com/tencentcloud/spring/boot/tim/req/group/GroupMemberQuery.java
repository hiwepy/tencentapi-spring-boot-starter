package com.tencentcloud.spring.boot.tim.req.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class GroupMemberQuery {

    /**
     * 群组 ID（必填）
     */
    @JsonProperty("GroupId")
    private String groupId;

    /**
     * 一次最多获取多少个成员的资料，不得超过6000。如果不填，则获取群内全部成员的信息
     */
    @JsonProperty("Limit")
    private Integer limit;

    /**
     * 从第几个成员开始获取，如果不填则默认为0，表示从第一个成员开始获取
     */
    @JsonProperty("Offset")
    private Integer offset;
    
    /**
     * 基础信息字段过滤器，指定需要获取的基础信息字段，基础信息字段详情请参阅 群基础资料
     */
    @JsonProperty("GroupBaseInfoFilter")
    private List<String> baseInfoFilter;

    /**
     * 基础信息字段过滤器，指定需要获取的基础信息字段，基础信息字段详情请参阅 群基础资料
     */
    @JsonProperty("MemberInfoFilter")
    private List<String> memberInfoFilter;

    /**
     * 该字段用来群组维度的自定义字段过滤器，指定需要获取的群组维度的自定义字段，详情请参阅 自定义字段
     */
    @JsonProperty("AppDefinedDataFilter_Group")
    private List<String> appDefinedDataGroupFilter;
    
    /**
     * 该字段用来群成员维度的自定义字段过滤器，指定需要获取的群成员维度的自定义字段，详情请参阅 自定义字段
     */
    @JsonProperty("AppDefinedDataFilter_GroupMember")
    private List<String> appDefinedDataGroupMemberFilter;
    
}
