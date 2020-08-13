package com.tencentcloud.spring.boot.tim;

public enum IMRequestAddress {
	
	/**
	 * 独立模式帐号导入
	 */
	ACCOUNT_IMPORT("https://console.tim.qq.com/v4/im_open_login_svc/account_import?"),
	/**
	 * 独立模式账户批量导入
	 */
	MULTI_ACCOUNT_IMPORT("https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import?"),
	/**
	 * 帐号登录态失效接口
	 */
	KICK("https://console.tim.qq.com/v4/im_open_login_svc/kick?"),
	/**
	 * 单发单聊消息
	 */
	SEND_MSG("https://console.tim.qq.com/v4/openim/sendmsg?"),
	/**
	 * 获取APP中的所有群组
	 */
	GROUP_OPENHTTP_SVC("https://console.tim.qq.com/v4/group_open_http_svc/get_appid_group_list?"),
	/**
	 * 创建群组
	 */
	CREATE_GROUP("https://console.tim.qq.com/v4/group_open_http_svc/create_group?"),
	/**
	 * 获取群组详细资料
	 */
	GROUP_INFO("https://console.tim.qq.com/v4/group_open_http_svc/get_group_info?"),
	/**
	 * 增加群组成员
	 */
	ADD_GROUP_MEMBER("https://console.tim.qq.com/v4/group_open_http_svc/add_group_member?"),
	/**
	 * 删除群组成员
	 */
	DELETE_GROUP_MEMBER("https://console.tim.qq.com/v4/group_open_http_svc/delete_group_member?"),
	/**
	 * 解散群组
	 */
	DESTROY_GROUP("https://console.tim.qq.com/v4/group_open_http_svc/destroy_group?"),
	/**
	 * 发送群组普通消息
	 */
	SEND_GROUP_NORMAL_MESSAGE("https://console.tim.qq.com/v4/group_open_http_svc/send_group_msg?"),
	/**
	 * 查询群组成员
	 */
	GET_GROUP_MEMBER_INFO("https://console.tim.qq.com/v4/group_open_http_svc/get_group_member_info?"),
	/**
	 * 获取用户在线状态
	 */
	QUERY_STATE("https://console.tim.qq.com/v4/openim/querystate?"),
	/**
	 * 拉取资料
	 */
	PORTRAIT_GET("https://console.tim.qq.com/v4/profile/portrait_get?"),
	/**
	 * 设置资料
	 */
	PORTRAIT_SET("https://console.tim.qq.com/v4/profile/portrait_set?"),
	/**
	 * 群组禁言
	 */
	GROUP_BANNED("https://console.tim.qq.com/v4/group_open_http_svc/forbid_send_msg?"),

	;

	private String value;

	IMRequestAddress(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
