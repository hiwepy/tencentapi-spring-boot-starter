package com.tencentcloud.spring.boot.tim;

/**
 * https://cloud.tencent.com/document/product/269/1520
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 */
public enum TimApiAddress {
	
	// ---------------- 帐号管理 ------------------
	
	/**
	 * 导入单个帐号
	 */
	ACCOUNT_IMPORT("/v4/im_open_login_svc/account_import?"),
	/**
	 * 导入多个帐号
	 */
	MULTI_ACCOUNT_IMPORT("/v4/im_open_login_svc/multiaccount_import?"),
	/**
	 * 删除帐号
	 */
	ACCOUNT_DELETE("v4/im_open_login_svc/account_delete?"),
	/**
	 * 查询帐号
	 */
	ACCOUNT_CHECK("v4/im_open_login_svc/account_check?"),
	/**
	 * 失效帐号登录态
	 */
	ACCOUNT_KICK("v4/im_open_login_svc/kick?"),
	/**
	 * 查询帐号在线状态
	 */
	ACCOUNT_STATE("v4/openim/querystate?"),
	
	// ---------------- 单聊消息 ------------------
	
	/**
	 * 单发单聊消息
	 */
	SEND_MSG("/v4/openim/sendmsg?"),
	/**
	 * 批量发单聊消息
	 */
	SEND_BATCH_MSG("v4/openim/batchsendmsg?"),
	/**
	 * 导入单聊消息
	 */
	IMPORT_MSG("v4/openim/importmsg?"),
	/**
	 * 查询单聊消息
	 */
	ADMIN_GET_ROAMMSG("v4/openim/admin_getroammsg?"),
	/**
	 * 撤回单聊消息
	 */
	ADMIN_MSG_WITHDRAW("v4/openim/admin_msgwithdraw?"),
	
	// ---------------- 全员推送 ------------------
	
	/**
	 * 全员推送
	 */
	IM_PUSH("v4/all_member_push/im_push?"),
	/**
	 * 设置应用属性名称
	 */
	IM_SET_ATTR_NAME("v4/all_member_push/im_set_attr_name?"),
	/**
	 * 获取应用属性名称
	 */
	IM_GET_ATTR_NAME("v4/all_member_push/im_get_attr_name?"),
	/**
	 * 获取用户属性
	 */
	IM_GET_ATTR("v4/all_member_push/im_get_attr?"),
	/**
	 * 设置用户属性
	 */
	IM_SET_ATTR("v4/all_member_push/im_set_attr?"),
	/**
	 * 删除用户属性
	 */
	IM_REMOVE_ATTR("v4/all_member_push/im_remove_attr?"),
	/**
	 * 获取用户标签
	 */
	IM_GET_TAG("v4/all_member_push/im_get_tag?"),
	/**
	 * 添加用户标签
	 */
	IM_ADD_TAG("v4/all_member_push/im_add_tag?"),
	/**
	 * 删除用户标签
	 */
	IM_REMOVE_TAG("v4/all_member_push/im_remove_tag?"),
	/**
	 * 删除用户所有标签
	 */
	IM_REMOVE_ALL_TAGS("v4/all_member_push/im_remove_all_tags?"),
	
	// ---------------- 资料管理 ------------------
	
	/**
	 * 拉取资料
	 */
	PORTRAIT_GET("/v4/profile/portrait_get?"),
	/**
	 * 设置资料
	 */
	PORTRAIT_SET("/v4/profile/portrait_set?"),
	
	
	  /**
     * 添加黑名单
     */
    BLACK_LIST_ADD("/v4/sns/black_list_add?"),
    /**
     * 删除黑名单
     */
    BLACK_LIST_DELETE("/v4/sns/black_list_delete?"),
    /**
     * 拉取黑名单
     */
    BLACK_LIST_GET("/v4/sns/black_list_get?"),
    /**
     * 校验黑名单
     */
    BLACK_LIST_CHECK("/v4/sns/black_list_check?"),
    
	/**
	 * 获取APP中的所有群组
	 */
	GROUP_OPENHTTP_SVC("/v4/group_open_http_svc/get_appid_group_list?"),
	/**
	 * 创建群组
	 */
	CREATE_GROUP("/v4/group_open_http_svc/create_group?"),
	/**
	 * 获取群组详细资料
	 */
	GROUP_INFO("/v4/group_open_http_svc/get_group_info?"),
	/**
	 * 增加群组成员
	 */
	ADD_GROUP_MEMBER("/v4/group_open_http_svc/add_group_member?"),
	/**
	 * 删除群组成员
	 */
	DELETE_GROUP_MEMBER("/v4/group_open_http_svc/delete_group_member?"),
	/**
	 * 解散群组
	 */
	DESTROY_GROUP("/v4/group_open_http_svc/destroy_group?"),
	/**
	 * 发送群组普通消息
	 */
	SEND_GROUP_NORMAL_MESSAGE("/v4/group_open_http_svc/send_group_msg?"),
	/**
	 * 查询群组成员
	 */
	GET_GROUP_MEMBER_INFO("/v4/group_open_http_svc/get_group_member_info?"),
	
	/**
	 * 群组禁言
	 */
	GROUP_BANNED("/v4/group_open_http_svc/forbid_send_msg?"),

	;

	private String value;

	TimApiAddress(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String fullpath(String prefix){
        return prefix.concat(value);
    }
 
}
