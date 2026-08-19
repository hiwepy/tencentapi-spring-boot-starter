package com.tencentcloud.spring.boot.tim.req.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 公用K V
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Data
public class MapKV {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Value")
    private  String value;

}
