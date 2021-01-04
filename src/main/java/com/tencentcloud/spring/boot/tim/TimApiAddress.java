package com.tencentcloud.spring.boot.tim;

/**
 * https://cloud.tencent.com/document/product/269/1520
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
public enum TimApiAddress {
	
	// ---------------- 帐号管理 ------------------
	
	/**
	 * 导入单个帐号
	 */
	ACCOUNT_IMPORT(TencentTimOperations.PREFIX + "/v4/im_open_login_svc/account_import?"),
	/**
	 * 导入多个帐号
	 */
	MULTI_ACCOUNT_IMPORT(TencentTimOperations.PREFIX + "/v4/im_open_login_svc/multiaccount_import?"),
	/**
	 * 删除帐号
	 */
	ACCOUNT_DELETE(TencentTimOperations.PREFIX + "/v4/im_open_login_svc/account_delete?"),
	/**
	 * 查询帐号
	 */
	ACCOUNT_CHECK(TencentTimOperations.PREFIX + "/v4/im_open_login_svc/account_check?"),
	/**
	 * 失效帐号登录态
	 */
	ACCOUNT_KICK(TencentTimOperations.PREFIX + "/v4/im_open_login_svc/kick?"),
	/**
	 * 查询帐号在线状态
	 */
	ACCOUNT_STATE(TencentTimOperations.PREFIX + "/v4/openim/querystate?"),
	
	// ---------------- 单聊消息 ------------------
	
	/**
	 * 单发单聊消息
	 */
	SEND_MSG(TencentTimOperations.PREFIX + "/v4/openim/sendmsg?"),
	/**
	 * 批量发单聊消息
	 */
	SEND_BATCH_MSG(TencentTimOperations.PREFIX + "/v4/openim/batchsendmsg?"),
	/**
	 * 导入单聊消息
	 */
	IMPORT_MSG(TencentTimOperations.PREFIX + "/v4/openim/importmsg?"),
	/**
	 * 查询单聊消息
	 */
	ADMIN_GET_ROAMMSG(TencentTimOperations.PREFIX + "/v4/openim/admin_getroammsg?"),
	/**
	 * 撤回单聊消息
	 */
	ADMIN_MSG_WITHDRAW(TencentTimOperations.PREFIX + "/v4/openim/admin_msgwithdraw?"),
	/**
	 * 设置单聊消息已读
	 */
	ADMIN_SET_MSG_READ(TencentTimOperations.PREFIX + "/v4/openim/admin_set_msg_read?"),
	
	// ---------------- 全员推送 ------------------
	
	/**
	 * 全员推送
	 */
	IM_PUSH(TencentTimOperations.PREFIX + "/v4/all_member_push/im_push?"),
	/**
	 * 设置应用属性名称
	 */
	IM_SET_ATTR_NAME(TencentTimOperations.PREFIX + "/v4/all_member_push/im_set_attr_name?"),
	/**
	 * 获取应用属性名称
	 */
	IM_GET_ATTR_NAME(TencentTimOperations.PREFIX + "/v4/all_member_push/im_get_attr_name?"),
	/**
	 * 获取用户属性
	 */
	IM_GET_ATTR(TencentTimOperations.PREFIX + "/v4/all_member_push/im_get_attr?"),
	/**
	 * 设置用户属性
	 */
	IM_SET_ATTR(TencentTimOperations.PREFIX + "/v4/all_member_push/im_set_attr?"),
	/**
	 * 删除用户属性
	 */
	IM_REMOVE_ATTR(TencentTimOperations.PREFIX + "/v4/all_member_push/im_remove_attr?"),
	/**
	 * 获取用户标签
	 */
	IM_GET_TAG(TencentTimOperations.PREFIX + "/v4/all_member_push/im_get_tag?"),
	/**
	 * 添加用户标签
	 */
	IM_ADD_TAG(TencentTimOperations.PREFIX + "/v4/all_member_push/im_add_tag?"),
	/**
	 * 删除用户标签
	 */
	IM_REMOVE_TAG(TencentTimOperations.PREFIX + "/v4/all_member_push/im_remove_tag?"),
	/**
	 * 删除用户所有标签
	 */
	IM_REMOVE_ALL_TAGS(TencentTimOperations.PREFIX + "/v4/all_member_push/im_remove_all_tags?"),
	
	// ---------------- 资料管理 ------------------
	
	/**
	 * 拉取资料
	 */
	PORTRAIT_GET(TencentTimOperations.PREFIX + "/v4/profile/portrait_get?"),
	/**
	 * 设置资料
	 */
	PORTRAIT_SET(TencentTimOperations.PREFIX + "/v4/profile/portrait_set?"),
	
	// ---------------- 关系链管理 ------------------
	
	/**
	 * 添加好友
	 */
	FRIEND_ADD(TencentTimOperations.PREFIX + "/v4/sns/friend_add?"),
	/**
	 * 导入好友
	 */
	FRIEND_IMPORT(TencentTimOperations.PREFIX + "/v4/sns/friend_import?"),
	/**
	 * 更新好友
	 */
	FRIEND_UPDATE(TencentTimOperations.PREFIX + "/v4/sns/friend_update?"),
	/**
	 * 删除好友
	 */
	FRIEND_DELETE(TencentTimOperations.PREFIX + "/v4/sns/friend_delete?"),
	/**
	 * 删除所有好友
	 */
	FRIEND_DELETE_ALL(TencentTimOperations.PREFIX + "/v4/sns/friend_delete_all?"),
	/**
	 * 校验好友
	 */
	FRIEND_CHECK(TencentTimOperations.PREFIX + "/v4/sns/friend_check?"),
	/**
	 * 拉取好友
	 */
	FRIEND_GET(TencentTimOperations.PREFIX + "/v4/sns/friend_get?"),
	/**
	 * 拉取指定好友
	 */
	FRIEND_GET_LIST(TencentTimOperations.PREFIX + "/v4/sns/friend_get_list?"),
	/**
	 * 添加黑名单
	 */
	BLACK_LIST_ADD(TencentTimOperations.PREFIX + "/v4/sns/black_list_add?"),
	/**
	 * 删除黑名单
	 */
	BLACK_LIST_DELETE(TencentTimOperations.PREFIX + "/v4/sns/black_list_delete?"),
	/**
	 * 拉取黑名单
	 */
	BLACK_LIST_GET(TencentTimOperations.PREFIX + "/v4/sns/black_list_get?"),
	/**
	 * 校验黑名单
	 */
	BLACK_LIST_CHECK(TencentTimOperations.PREFIX + "/v4/sns/black_list_check?"),
	/**
	 * 添加分组
	 */
	GROUP_ADD(TencentTimOperations.PREFIX + "/v4/sns/group_add?"),
	/**
	 *删除分组
	 */
	GROUP_DELETE(TencentTimOperations.PREFIX + "/v4/sns/group_delete?"),
	
	// ---------------- 群组管理 ------------------
	
	/**
	 * 获取APP中的所有群组
	 */
	GROUP_OPENHTTP_SVC(TencentTimOperations.PREFIX +"/v4/group_open_http_svc/get_appid_group_list?"),
	
	/**
	 * 创建群组
	 */
	CREATE_GROUP(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/create_group?"),
	/**
	 * 获取群组详细资料
	 */
	GET_GROUP_INFO(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/get_group_info?"),
	/**
	 * 获取群组详细资料
	 */
	GET_GROUP_MEMBER_INFO(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/get_group_member_info?"),
	/**
	 * 获取群组详细资料
	 */
	MODIFY_GROUP_BASE_INFO(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/modify_group_base_info?"),
	/**
	 * 增加群组成员
	 */
	ADD_GROUP_MEMBER(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/add_group_member?"),
	/**
	 * 删除群组成员
	 */
	DELETE_GROUP_MEMBER(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/delete_group_member?"),
	/**
	 * 修改群成员资料
	 */
	MODIFY_GROUP_MEMBER_INFO(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/modify_group_member_info?"),
	/**
	 * 解散群组
	 */
	DESTROY_GROUP(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/destroy_group?"),
	/**
	 * 获取用户所加入的群组
	 */
	GET_JOINED_GROUP_LIST(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/get_joined_group_list?"),
	/**
	 * 查询用户在群组中的身份
	 */
	GET_ROLE_IN_GROUP(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/get_role_in_group?"),
	/**
	 * 批量禁言和取消禁言
	 */
	FORBID_SEND_MSG(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/forbid_send_msg?"),
	/**
	 * 获取被禁言群成员列表
	 */
	GET_GROUP_SHUTTED_UIN(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/get_group_shutted_uin?"),
	/**
	 * 在群组中发送普通消息
	 */
	SEND_GROUP_MSG(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/send_group_msg?"),
	/**
	 * 在群组中发送系统通知
	 */
	SEND_GROUP_SYSTEM_NOTIFICATION(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/send_group_system_notification?"),
	/**
	 *撤回群消息
	 */
	GROUP_MSG_RECALL(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/group_msg_recall?"),
	/**
	 *  转让群主
	 */
	CHANGE_GROUP_OWNER(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/change_group_owner?"),
	/**
	 * 导入群基础资料
	 */
	IMPORT_GROUP(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/import_group?"),
	/**
	 * 导入群消息
	 */
	IMPORT_GROUP_MSG(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/import_group_msg?"),
	/**
	 * 导入群基础资料
	 */
	IMPORT_GROUP_MEMBER(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/import_group_member?"),
	/**
	 * 设置成员未读消息计数
	 */
	SET_UNREAD_MSG_NUM(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/set_unread_msg_num?"),
	/**
	 * 删除指定用户发送的消息
	 */
	DELETE_GROUP_MSG_BY_SENDER(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/delete_group_msg_by_sender?"),
	/**
	 * 拉取群历史消息
	 */
	GROUP_MSG_GET_SIMPLE(TencentTimOperations.PREFIX + "/v4/group_open_http_svc/group_msg_get_simple?"),
	
	// ---------------- 全局禁言管理 ------------------

	/**
	 *设置全局禁言
	 */
	SET_NO_SPEAKING(TencentTimOperations.PREFIX + "/v4/openconfigsvr/setnospeaking?"),
	/**
	 *查询全局禁言
	 */
	GET_NO_SPEAKING(TencentTimOperations.PREFIX + "/v4/openconfigsvr/getnospeaking?"),
	
	// ---------------- 运营管理 ------------------
	
	/**
	 * 拉取运营数据
	 */
	GET_APP_INFO(TencentTimOperations.PREFIX + "/v4/openconfigsvr/getappinfo?"),
	/**
	 * 下载消息记录
	 */
	GET_HISTORY(TencentTimOperations.PREFIX + "/v4/open_msg_svc/get_history?"),
	/**
	 * 获取服务器 IP 地址
	 */
	GET_IP_LIST(TencentTimOperations.PREFIX + "/v4/ConfigSvc/GetIPList?");

	private String value;

	TimApiAddress(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
 
}
