package com.tencentcloud.spring.boot.tim.req.profile;

/**
 * 加好友验证方式
 */
public enum AllowType {

	ALLOWTYPE_TYPE_NEEDCONFIRM("AllowType_Type_NeedConfirm","需要经过自己确认对方才能添加自己为好友"),
	ALLOWTYPE_TYPE_ALLOWANY("AllowType_Type_AllowAny","允许任何人添加自己为好友"),
	ALLOWTYPE_TYPE_DENYANY("AllowType_Type_DenyAny","不允许任何人添加自己为好友");
	
    private String value;
    private String desc;

    AllowType(String value,String desc){
        this.value = value;
        this.desc = desc;
    }

    public String getValue(){
        return this.value;
    }
    
	public String getDesc() {
		return desc;
	}
	
}
