package com.tencentcloud.spring.boot.tim.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL)
public class ProfileItem {
    /**
     * 返回的资料对象的名称：
     */
    @JsonProperty("Tag")
    private String tag;
    /**
     * 拉取的资料对象的值：
     */
    @JsonProperty("Value")
    private Object value;
}
