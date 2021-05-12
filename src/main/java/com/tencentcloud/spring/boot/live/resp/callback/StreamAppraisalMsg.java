package com.tencentcloud.spring.boot.live.resp.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 4、截图事件通知 https://cloud.tencent.com/document/product/267/47029
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamAppraisalMsg {

	/**
	 * 事件通知安全签名 sign = MD5（key + t）。 说明：腾讯云把加密 key 和 t 进行字符串拼接后通过 MD5 计算得出 sign
	 * 值，并将其放在通知消息里，您的后台服务器在收到通知消息后可以根据同样的算法确认 sign 是否正确，进而确认消息是否确实来自腾讯云后台。
	 */
	@JsonProperty(value = "sign")
	private String sign;

	/**
	 * 过期时间，事件通知签名过期 UNIX 时间戳。 来自腾讯云的消息通知默认过期时间是10分钟，如果一条消息通知中的 t
	 * 值所指定的时间已经过期，则可以判定这条通知无效，进而可以防止网络重放攻击。 t 的格式为十进制 UNIX
	 * 时间戳，即从1970年01月01日（UTC/GMT 的午夜）开始所经过的秒数。
	 */
	@JsonProperty(value = "t")
	private Long t;

	/**
	 * 事件类型编码
	 */
	@JsonProperty(value = "event_type")
	private String event_type;

	/**
	 * 预警策略 ID，视频内容预警：20001
	 */
	@JsonProperty(value = "tid")
	private String tid;

	/**
	 * 流名称
	 */
	@JsonProperty(value = "streamId")
	private String streamId;

	/**
	 * 频道 ID
	 */
	@JsonProperty(value = "channelId")
	private String channelId;

	/**
	 * 预警图片链接
	 */
	@JsonProperty(value = "img")
	private String img;

	/**
	 * 图片类型，0：正常图片，1 - 5：不合宜图片
	 */
	@JsonProperty(value = "type")
	private String[] type;

	/**
	 * 涉黄置信度，范围 0-100；normalScore， hotScore， pornScore 的综合评分
	 */
	@JsonProperty(value = "confidence")
	private int confidence;

	/**
	 * 图片为正常图片的评分
	 */
	@JsonProperty(value = "normalScore")
	private int normalScore;

	/**
	 * 图片为性感图片的评分
	 */
	@JsonProperty(value = "hotScore")
	private int hotScore;

	/**
	 * 图片为色情图片的评分
	 */
	@JsonProperty(value = "pornScore")
	private int pornScore;

	/**
	 * 图片的级别
	 */
	@JsonProperty(value = "level")
	private int level;

	/**
	 * 图片的 OCR 识别信息（如果存在）
	 */
	@JsonProperty(value = "ocrMsg")
	private String ocrMsg;

	/**
	 * 截图时间
	 */
	@JsonProperty(value = "screenshotTime")
	private Long screenshotTime;

	/**
	 * 请求发送时间，UNIX 时间戳
	 */
	@JsonProperty(value = "sendTime")
	private Long sendTime;

	/**
	 * 请图片相识度评分
	 */
	@JsonProperty(value = "similarScore")
	private int similarScore;

	/**
	 * 用户 APPID
	 */
	@JsonProperty(value = "appid")
	private String appid;

	/**
	 * 推流域名
	 */
	@JsonProperty(value = "app")
	private String app;

	/**
	 * 推流路径
	 */
	@JsonProperty(value = "appname")
	private String appname;

	/**
	 * 直播流名称
	 */
	@JsonProperty(value = "stream_id")
	private String stream_id;

	/**
	 * 用户推流 URL 所带参数
	 */
	@JsonProperty(value = "stream_param")
	private String stream_param;

	/**
	 * 游戏详情信息
	 */
	@JsonProperty(value = "gameDetails")
	private List<GameDetail> gameDetails;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public class GameDetail {

		/**
		 * 绝地求生信息
		 */
		@JsonProperty("battlegrounds")
		private String battlegrounds;

		/**
		 * 游戏列表
		 */
		@JsonProperty("gameList")
		private List<GameInfo> gameList;

	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public class GameInfo {

		/**
		 * 游戏名称
		 */
		@JsonProperty("name")
		private String name;

		/**
		 * 概率
		 */
		@JsonProperty("confidence")
		private int confidence;

	}

}
