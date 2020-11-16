package com.tencentcloud.spring.boot;


public class TencentTimConstants {

	public static class Action {

		public static final String LOGIN = "Login"; // 表示上线（TCP 建立）
		public static final String LOGOUT = "Logout"; // 表示下线（TCP 断开）
		public static final String DISCONNECT = "Disconnect"; // 表示网络断开（TCP 断开）
		
	}
	
	/**
	   * 返回的用户状态，目前支持的状态有：
	   *前台运行状态（Online）：客户端登录后和即时通信 IM 后台有长连接
	   * 后台运行状态（PushOnline）：iOS 和 Android 进程被 kill 或因网络问题掉线，进入 PushOnline 状态，此时仍然可以接收消息的离线推送。客户端切到后台，但是进程未被手机操作系统 kill 掉时，此时状态仍是 Online
	   *未登录状态（Offline）：客户端主动退出登录或者客户端自上一次登录起7天之内未登录过
	   *如果用户是多终端登录，则只要有一个终端的状态是 Online ，该字段值就是 Online
	   */
	public static class State {

		public static final String ONLINE = "Online"; // 前台运行状态（Online）：客户端登录后和即时通信 IM 后台有长连接
		public static final String PUSHONLINE = "PushOnline"; // 后台运行状态（PushOnline）：iOS 和 Android 进程被 kill 或因网络问题掉线，进入 PushOnline 状态，此时仍然可以接收消息的离线推送。客户端切到后台，但是进程未被手机操作系统 kill 掉时，此时状态仍是 Online
		public static final String OFFLINE = "Offline"; // 未登录状态（Offline）：客户端主动退出登录或者客户端自上一次登录起7天之内未登录过
		
	}
	
	public static class CallBack {
		
		/**
		 * 1、状态变更回调
		 * https://cloud.tencent.com/document/product/269/2570
		 */
		public static final String STATE_CHANGE = "State.StateChange";
		/**
		 * 2、添加好友之后回调
		 * https://cloud.tencent.com/document/product/269/1657
		 */
		public static final String SNS_FRIEND_ADD = "Sns.CallbackFriendAdd";
		/**
		 * 3、删除好友之后回调
		 * https://cloud.tencent.com/document/product/269/1659
		 */
		public static final String SNS_FRIEND_DELETE = "Sns.CallbackFriendDelete";
		/**
		 * 4、添加黑名单之后回调
		 * https://cloud.tencent.com/document/product/269/1660
		 */
		public static final String SNS_BLACKLIST_ADD = "Sns.CallbackBlackListAdd";
		/**
		 * 5、删除黑名单之后回调
		 * https://cloud.tencent.com/document/product/269/1661
		 */
		public static final String SNS_BLACKLIST_DELETE = "Sns.CallbackBlackListDelete";
		/**
		 * 6、发单聊消息之前回调
		 * https://cloud.tencent.com/document/product/269/1632
		 */
		public static final String C2C_BEFORE_SEND_MSG = "C2C.CallbackBeforeSendMsg";
		/**
		 * 7、发单聊消息之后回调
		 * https://cloud.tencent.com/document/product/269/2716
		 */
		public static final String C2C_AFTER_SEND_MSG = "C2C.CallbackAfterSendMsg";
		/**
		 * 8、创建群组之前回调
		 * https://cloud.tencent.com/document/product/269/1662
		 */
		public static final String GROUP_BEFORE_CREATE = "Group.CallbackBeforeCreateGroup";
		/**
		 * 9、创建群组之后回调
		 * https://cloud.tencent.com/document/product/269/1663
		 */
		public static final String GROUP_AFTER_CREATE = "Group.CallbackAfterCreateGroup";
		/**
		 * 10、申请入群之前回调
		 * https://cloud.tencent.com/document/product/269/1665
		 */
		public static final String GROUP_BEFORE_APPLY_JOIN = "Group.CallbackBeforeApplyJoinGroup";
		/**
		 * 11、拉人入群之前回调
		 * https://cloud.tencent.com/document/product/269/1666
		 */
		public static final String GROUP_BEFORE_INVITE_JOIN = "Group.CallbackBeforeInviteJoinGroup";
		/**
		 * 12、新成员入群之后回调
		 * https://cloud.tencent.com/document/product/269/1667
		 */
		public static final String GROUP_AFTER_MEMBER_JOIN = "Group.CallbackAfterNewMemberJoin";
		/**
		 * 13、群成员离开之后回调
		 * https://cloud.tencent.com/document/product/269/1668
		 */
		public static final String GROUP_AFTER_MEMBER_EXIT = "Group.CallbackAfterMemberExit";
		/**
		 * 14、群内发言之前回调
		 * https://cloud.tencent.com/document/product/269/1619
		 */
		public static final String GROUP_BEFORE_SEND_MSG = "Group.CallbackBeforeSendMsg";
		/**
		 * 15、群内发言之后回调
		 * https://cloud.tencent.com/document/product/269/2661
		 */
		public static final String GROUP_AFTER_SEND_MSG = "Group.CallbackAfterSendMsg";
		/**
		 * 16、群组满员之后回调
		 * https://cloud.tencent.com/document/product/269/1669
		 */
		public static final String GROUP_AFTER_GROUP_FULL = "Group.CallbackAfterGroupFull";
		/**
		 * 17、群组解散之后回调
		 * https://cloud.tencent.com/document/product/269/1670
		 */
		public static final String GROUP_AFTER_GROUP_DESTROYED = "Group.CallbackAfterGroupDestroyed";
		/**
		 * 18、群组资料修改之后回调
		 * https://cloud.tencent.com/document/product/269/2930
		 */
		public static final String GROUP_AFTER_GROUP_INFO_CHANGED = "Group.CallbackAfterGroupInfoChanged";
	}
	
	public static class Reason {

		public static final String LINK_CLOSE = "LinkClose";
		public static final String TIME_OUT = "TimeOut";
		
	}
	
}
