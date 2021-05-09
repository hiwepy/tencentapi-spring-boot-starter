package com.tencentcloud.spring.boot.tim.req.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class GroupResponseFilter {

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
