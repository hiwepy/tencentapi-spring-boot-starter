package com.tencentcloud.spring.boot.tim;

public enum EGroupType {
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

    EGroupType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
