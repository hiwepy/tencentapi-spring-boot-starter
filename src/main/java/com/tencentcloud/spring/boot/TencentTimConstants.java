package com.tencentcloud.spring.boot;


/**
 * String constants used by the Tencent Cloud IM (TIM) integration: client
 * connection actions, user online states, callback event types and disconnect
 * reasons.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class TencentTimConstants {

	/** Client connection lifecycle actions reported by TIM. */
	public static class Action {

		/** Client came online (TCP connection established). */
		public static final String LOGIN = "Login";
		/** Client went offline (TCP connection closed). */
		public static final String LOGOUT = "Logout";
		/** Network disconnected (TCP connection lost). */
		public static final String DISCONNECT = "Disconnect";

	}

	/**
	 * User online states returned by TIM.
	 * <p>Supported values:</p>
	 * <ul>
	 *   <li><b>Online</b> &mdash; the client is logged in and has a long-lived
	 *       connection to the IM backend.</li>
	 *   <li><b>PushOnline</b> &mdash; the iOS/Android process was killed or the
	 *       network dropped, so the account can still receive offline push
	 *       notifications. While the app is backgrounded but the process is still
	 *       alive the state remains {@code Online}.</li>
	 *   <li><b>Offline</b> &mdash; the client logged out or has not logged in
	 *       within the last 7 days.</li>
	 * </ul>
	 * <p>For multi-terminal users the value is {@code Online} as long as any one
	 * terminal is online.</p>
	 */
	public static class State {

		/** Foreground running state: the client is logged in and connected. */
		public static final String ONLINE = "Online";
		/** Background running state: process killed or network dropped; offline push is still available. */
		public static final String PUSHONLINE = "PushOnline";
		/** Logged out: the client logged out or has not logged in within the last 7 days. */
		public static final String OFFLINE = "Offline";

	}

	/** Callback event types delivered by the TIM backend. */
	public static class CallBack {

		/**
		 * State change callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/2570">docs</a>
		 */
		public static final String STATE_CHANGE = "State.StateChange";
		/**
		 * After a friend is added callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1657">docs</a>
		 */
		public static final String SNS_FRIEND_ADD = "Sns.CallbackFriendAdd";
		/**
		 * After a friend is deleted callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1659">docs</a>
		 */
		public static final String SNS_FRIEND_DELETE = "Sns.CallbackFriendDelete";
		/**
		 * After an account is added to the blacklist callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1660">docs</a>
		 */
		public static final String SNS_BLACKLIST_ADD = "Sns.CallbackBlackListAdd";
		/**
		 * After an account is removed from the blacklist callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1661">docs</a>
		 */
		public static final String SNS_BLACKLIST_DELETE = "Sns.CallbackBlackListDelete";
		/**
		 * Before a C2C (single-chat) message is sent callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1632">docs</a>
		 */
		public static final String C2C_BEFORE_SEND_MSG = "C2C.CallbackBeforeSendMsg";
		/**
		 * After a C2C (single-chat) message is sent callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/2716">docs</a>
		 */
		public static final String C2C_AFTER_SEND_MSG = "C2C.CallbackAfterSendMsg";
		/**
		 * Before a group is created callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1662">docs</a>
		 */
		public static final String GROUP_BEFORE_CREATE = "Group.CallbackBeforeCreateGroup";
		/**
		 * After a group is created callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1663">docs</a>
		 */
		public static final String GROUP_AFTER_CREATE = "Group.CallbackAfterCreateGroup";
		/**
		 * Before a join-group application is submitted callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1665">docs</a>
		 */
		public static final String GROUP_BEFORE_APPLY_JOIN = "Group.CallbackBeforeApplyJoinGroup";
		/**
		 * Before members are invited into a group callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1666">docs</a>
		 */
		public static final String GROUP_BEFORE_INVITE_JOIN = "Group.CallbackBeforeInviteJoinGroup";
		/**
		 * After a new member joins a group callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1667">docs</a>
		 */
		public static final String GROUP_AFTER_MEMBER_JOIN = "Group.CallbackAfterNewMemberJoin";
		/**
		 * After a group member leaves callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1668">docs</a>
		 */
		public static final String GROUP_AFTER_MEMBER_EXIT = "Group.CallbackAfterMemberExit";
		/**
		 * Before a message is sent in a group callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1619">docs</a>
		 */
		public static final String GROUP_BEFORE_SEND_MSG = "Group.CallbackBeforeSendMsg";
		/**
		 * After a message is sent in a group callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/2661">docs</a>
		 */
		public static final String GROUP_AFTER_SEND_MSG = "Group.CallbackAfterSendMsg";
		/**
		 * After a group becomes full callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1669">docs</a>
		 */
		public static final String GROUP_AFTER_GROUP_FULL = "Group.CallbackAfterGroupFull";
		/**
		 * After a group is dissolved callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/1670">docs</a>
		 */
		public static final String GROUP_AFTER_GROUP_DESTROYED = "Group.CallbackGroupDestroyed";
		/**
		 * After group profile information is changed callback.
		 * @see <a href="https://cloud.tencent.com/document/product/269/2930">docs</a>
		 */
		public static final String GROUP_AFTER_GROUP_INFO_CHANGED = "Group.CallbackAfterGroupInfoChanged";
	}

	/** Reasons reported when a client connection ends. */
	public static class Reason {

		/** The long connection was actively closed. */
		public static final String LINK_CLOSE = "LinkClose";
		/** The connection timed out. */
		public static final String TIME_OUT = "TimeOut";

	}

}
