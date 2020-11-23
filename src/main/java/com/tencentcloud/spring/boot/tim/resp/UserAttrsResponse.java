package com.tencentcloud.spring.boot.tim.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 用户属性
 */
@Data
@JsonInclude( JsonInclude.Include.NON_NULL)
public class UserAttrsResponse {

    /**
     * 用户帐号
     */
    @JsonProperty("To_Account")
    private String toAccount;

    /**
     * 属性内容
     */
    @JsonProperty("Attrs")
    private AttrsResponse attrs;

}
