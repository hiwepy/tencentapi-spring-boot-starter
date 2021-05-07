package com.tencentcloud.spring.boot.tim.resp.account;

import java.util.NoSuchElementException;

/**
 * 返回的用户状态，目前支持的状态有：
 *前台运行状态（Online）：客户端登录后和即时通信 IM 后台有长连接
 * 后台运行状态（PushOnline）：iOS 和 Android 进程被 kill 或因网络问题掉线，进入 PushOnline 状态，此时仍然可以接收消息的离线推送。客户端切到后台，但是进程未被手机操作系统 kill 掉时，此时状态仍是 Online
 *未登录状态（Offline）：客户端主动退出登录或者客户端自上一次登录起7天之内未登录过
 *如果用户是多终端登录，则只要有一个终端的状态是 Online ，该字段值就是 Online
 */
public enum AccountState {
	
	/**
	 * 前台运行状态（Online）：客户端登录后和即时通信 IM 后台有长连接
	 */
	ONLINE("Online"),
	/**
	 * 后台运行状态（PushOnline）：iOS 和 Android 进程被 kill 或因网络问题掉线，进入 PushOnline 状态，此时仍然可以接收消息的离线推送。客户端切到后台，但是进程未被手机操作系统 kill 掉时，此时状态仍是 Online
	 */
	PUSHONLINE("PushOnline"),
	/**
	 * 未登录状态（Offline）：客户端主动退出登录或者客户端自上一次登录起7天之内未登录过
	 */
	OFFLINE("Offline");

	private String state;

	private AccountState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public boolean equals(AccountState state) {
		return this.compareTo(state) == 0;
	}

	public boolean equals(String state) {
		return this.compareTo(AccountState.valueOfIgnoreCase(state)) == 0;
	}

	public static AccountState valueOfIgnoreCase(String state) {
		for (AccountState stateEnum : AccountState.values()) {
			if (stateEnum.name().equalsIgnoreCase(state)) {
				return stateEnum;
			}
		}
		throw new NoSuchElementException("Cannot found AccountState with key '" + state + "'.");
	}
	 
}