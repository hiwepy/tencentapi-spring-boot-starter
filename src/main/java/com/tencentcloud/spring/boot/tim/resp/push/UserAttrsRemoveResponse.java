package com.tencentcloud.spring.boot.tim.resp.push;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 用户属性
 */
@Data
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAttrsRemoveResponse {

    /**
     * 用户帐号
     */
    @JsonProperty("To_Account")
    private String toAccount;

    /**
     * 属性内容
     */
    @JsonProperty("Attrs")
    private List<String> attrs;

}
