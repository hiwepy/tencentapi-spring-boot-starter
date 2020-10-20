package com.tencentcloud.spring.boot;

public class TencentTimConstants {

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
		public static final String GROUP_BEFORE_CREATE_GROUP = "Group.CallbackBeforeCreateGroup";
		/**
		 * 9、创建群组之后回调
		 * https://cloud.tencent.com/document/product/269/1663
		 */
		public static final String GROUP_AFTER_CREATE_GROUP = "Group.CallbackAfterCreateGroup";
		/**
		 * 10、申请入群之前回调
		 * https://cloud.tencent.com/document/product/269/1665
		 */
		public static final String GROUP_BEFORE_APPLY_JOIN_GROUP = "Group.CallbackBeforeApplyJoinGroup";
		/**
		 * 11、拉人入群之前回调
		 * https://cloud.tencent.com/document/product/269/1666
		 */
		public static final String GROUP_BEFORE_INVITE_JOIN_GROUP = "Group.CallbackBeforeInviteJoinGroup";
		/**
		 * 12、新成员入群之后回调
		 * https://cloud.tencent.com/document/product/269/1667
		 */
		public static final String GROUP_AFTER_MEMBER_JOIN_GROUP = "Group.CallbackAfterNewMemberJoin";
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
	
}
