package com.tencentcloud.spring.boot.tim.resp.sns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlackListItem {

    /**
     * 黑名单的 UserID
     */
    @JsonProperty("To_Account")
    private String toAccount;

    /**
     * 添加黑名单的时间
     */
    @JsonProperty("AddBlackTimeStamp")
    private Long addBlackTimeStamp;
}
