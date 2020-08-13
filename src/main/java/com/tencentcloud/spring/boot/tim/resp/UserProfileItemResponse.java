package com.tencentcloud.spring.boot.tim.resp;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL)
public class UserProfileItemResponse {

    /**
     * 用户帐号
     */
    @JsonProperty("To_Account")
    private String toAccount;

    /**
     * 返回的用户的资料对象数组，数组中每一个对象都包含了 Tag 和 Value
     */
    @JsonProperty("ProfileItem")
    private String profileItem;

    /**
     * To_Account 的处理结果，0表示成功，非0表示失败
     */
    @JsonProperty("ResultCode")
    private String resultCode;

    /**
     * To_Account 的错误描述信息，成功时该字段为空
     */
    @JsonProperty("ResultInfo")
    private String resultInfo;

    /**
     * 返回处理失败的用户列表，仅当存在失败用户时才返回该字段
     */
    @JsonProperty("Fail_Account")
    private ArrayList<String> failAccount;

    /**
     * 请求处理的结果，“OK” 表示处理成功，“FAIL” 表示失败
     */
    @JsonProperty("ActionStatus")
    private String actionStatus;

    /**
     * 错误码，0表示成功，非0表示失败
     */
    @JsonProperty("ErrorCode")
    private Integer errorCode;

    /**
     * 详细错误信息
     */
    @JsonProperty("ErrorInfo")
    private String errorInfo;

    /**
     * 详细的客户端展示信息
     */
    @JsonProperty("ErrorDisplay")
    private String errorDisplay;


    public boolean isSuccess() {
        return "OK".equals(actionStatus);
    }
}
