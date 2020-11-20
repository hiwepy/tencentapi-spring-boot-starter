package com.tencentcloud.spring.boot.tim.resp.callback;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImageInfoArray {

    @JsonProperty(value = "Type")
    private Integer Type;

    @JsonProperty(value = "Size")
    private Integer Size;


    @JsonProperty(value = "Width")
    private Integer Width;


    @JsonProperty(value = "Height")
    private Integer Height;


    @JsonProperty(value = "URL")
    private String URL;

}
