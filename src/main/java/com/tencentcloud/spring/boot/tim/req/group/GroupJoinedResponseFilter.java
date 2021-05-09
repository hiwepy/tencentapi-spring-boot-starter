package com.tencentcloud.spring.boot.tim.req.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

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
