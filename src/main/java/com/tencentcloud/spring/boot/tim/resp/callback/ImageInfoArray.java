package com.tencentcloud.spring.boot.tim.resp.callback;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class ImageInfoArray {

	/**
	 *  图片类型： 1-原图，2-大图，3-缩略图。
	 */
	@JsonProperty(value = "Type")
	private Long type;
	
	/**
	 *  图片数据大小，单位：字节。
	 */
	@JsonProperty(value = "Size")
	private Long size;

	/**
	 *  图片宽度
	 */
    @JsonProperty(value = "Width")
    private Integer width;

    /**
	 *  图片高度
	 */
    @JsonProperty(value = "Height")
    private Integer height;

    /**
	 *  图片下载地址
	 */
    @JsonProperty(value = "URL")
    private String url;

}
