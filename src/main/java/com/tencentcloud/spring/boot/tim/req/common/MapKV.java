package com.tencentcloud.spring.boot.tim.req.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 公用K V
 */
@Data
public class MapKV {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Value")
    private  String value;

}
