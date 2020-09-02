package com.tencentcloud.spring.boot.tim.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class ResultItem {
    /**
     * 请求添加为黑名单的用户 UserID
     */
    @JsonProperty("To_Account")
    private String toAccount;
    /**
     * To_Account 的处理结果，0表示成功，非0表示失败
     */
    @JsonProperty("ResultCode")
    private Integer resultCode;
    /**
     * To_Account 的错误描述信息，成功时该字段为空
     */
    @JsonProperty("ResultInfo")
    private String resultInfo;
}
