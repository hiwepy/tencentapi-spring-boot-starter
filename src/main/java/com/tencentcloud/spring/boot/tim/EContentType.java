package com.tencentcloud.spring.boot.tim;

public enum EContentType {
    /**
     * 系统消息（1-100）
     */
    YSTEM_MESSAGE_NOTICE(1,"系统消息-提示文案"),
    SYSTEM_MESSAGE_JOIN_ROOM(2,"系统消息-进入房间"),
    SYSTEM_MESSAGE_LEAVE_ROOM(3,"系统消息-退出房间"),
    SYSTEM_MESSAGE_BLACK_USER(4,"系统消息-拉黑用户"),
    SYSTEM_MESSAGE_BANNED_USER(5,"系统消息-禁言用户"),
    SYSTEM_MESSAGE_CANCEL_BANNED_USER(6,"系统消息-解除禁言用户"),
    SYSTEM_MESSAGE_BECOME_FANS(7,"系统消息-成为粉丝"),

    /**
     * 礼物消息（101-200）
     */
    GIFT_MESSAGE_LUCKY(101,"礼物消息-幸运礼物"),
    GIFT_MESSAGE_LUXURIOUS(102,"礼物消息-奢华礼物"),
    GIFT_MESSAGE_ROCKET(103,"礼物消息-火箭"),
    GIFT_MESSAGE_FAIRY_STICK(104,"礼物消息-仙女棒"),
    GIFT_MESSAGE_SMALL_RED_ENVELOPE(105,"礼物消息-小红包"),
    GIFT_MESSAGE_BIG_RED_ENVELOPE(106,"礼物消息-大红包"),
    GIFT_MESSAGE_1314LUCKY(107,"礼物消息-1314档位幸运礼物"),
    GIFT_GRAND_PRIZE(108,"幸运礼物中大奖"),
    /**
     * 房间内数据推送（201-300）
     */
    ROOM_DATA_SEAT_LIST(201,"房间内数据推送-观众席列表"),
    ROOM_DATA_TODAY_INCOME(202,"房间内数据推送-今日收益"),
    ROOM_DATA_CHANGE_GAME(203,"房间内数据推送-切换游戏"),
    ROOM_DATA_BARRAGE(204,"房间内数据推送-弹幕"),
    /**
     * PK（301-400）
     */
    PK_INVITE_PROCESS(301,"PK流程控制"),
    PK_MATCH_RESULT(302,"PK匹配失败"),
    PK_FAIL(303,"PK异常"),
    PK_START(304,"PK开始"),
    PK_PROGRESS(305,"PK进度条"),
    PK_SETTLEMENT(306,"PK结算"),
    PK_END(307,"PK结束"),
//
    /**
     * 自定义消息（401-500）
     */
//    ROOM_DATA_SEAT_LIST(201,"房间内数据推送-观众席列表"),
//    ROOM_DATA_TODAY_INCOME(202,"房间内数据推送-今日收益"),
//    ROOM_DATA_RED_ENVELOPE(203,"房间内数据推送-红包"),
//    ROOM_DATA_CHANGE_GAME(204,"房间内数据推送-切换游戏"),
    CUSTOME_MESSAGE_STOP_LIVE(405,"自定义消息-主播关播"),


    /**
     * 全服广播（500-599）
     */
    GAME_GRAND_PRIZE(501,"游戏中大奖"),

    ;
    private Integer code;
    private String desc;

    EContentType(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode(){
        return this.code;
    }
}
