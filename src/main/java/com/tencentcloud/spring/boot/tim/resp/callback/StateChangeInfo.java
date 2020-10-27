package com.tencentcloud.spring.boot.tim.resp.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateChangeInfo {
	
	/**
	 * 用户上线或者下线的动作， Login 表示上线（TCP 建立）， Logout 表示下线（TCP 断开）， Disconnect 表示网络断开（TCP
	 * 断开）
	 */
	@JsonProperty(value = "Action")
	private String action;
	/**
	 * 用户 UserID
	 */
	@JsonProperty(value = "To_Account")
	private String account;
	/**
	 * 用户上下线触发的原因： Login 的原因有 Register：App TCP 连接建立 Logout 的原因有 Unregister：App
	 * 用户注销帐号导致 TCP 断开 Disconnect 的原因有 LinkClose：即时通信 IM 检测到 App TCP 连接断开 （例如 kill
	 * App，客户端发出 TCP 的 FIN 包或 RST 包）； TimeOut：即时通信 IM 检测到 App 心跳包超时， 认为 TCP
	 * 已断开（例如客户端网络异常断开，未发出 TCP 的 FIN 包或 RST 包，也无法发送心跳包）。心跳超时时间为 400 秒。
	 */
	@JsonProperty(value = "Reason")
	private String reason;
	
}
