package com.tencentcloud.spring.boot.tim.req.group;

/**
 * <p>Enumeration of Group Type types.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public enum GroupType {
	/**
	 * 私有群
	 */
	PRIVATE("Private"),
	/**
	 * 公开群
	 */
	PUBLIC("Public"),
	/**
	 * 聊天室
	 */
	CHAT_ROOM("ChatRoom"),
	/**
	 * 音视频聊天室
	 */
	AV_CHAT_ROOM("AVChatRoom"),
	/**
	 * 在线成员广播大群
	 */
	B_CHAT_ROOM("BChatRoom"),

	;

	private String value;

	GroupType(String value) {
		this.value = value;
	}

	/**
	 * Returns the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}
}
