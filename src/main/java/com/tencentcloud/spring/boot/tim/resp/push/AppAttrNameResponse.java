package com.tencentcloud.spring.boot.tim.resp.push;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用属性名称
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppAttrNameResponse extends TimActionResponse {

    /**	
     * 包含多个键对。每对键值对，表示第几个属性对应的名称。例如"0":"xxx"表示第0号属性的名称是 xxx
     */
    @JsonProperty("AttrNames")
    private Map<String, String> attrNames;
}
