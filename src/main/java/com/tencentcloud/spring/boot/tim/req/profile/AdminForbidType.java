package com.tencentcloud.spring.boot.tim.req.profile;

/**
 * 管理员禁止加好友标识
 */
public enum AdminForbidType {

	AdminForbid_Type_None("AdminForbid_Type_None","默认值，允许加好友"),
	AdminForbid_Type_SendOut("AdminForbid_Type_SendOut","禁止该用户发起加好友请求");
	
    private String value;
    private String desc;

    AdminForbidType(String value,String desc){
        this.value = value;
        this.desc = desc;
    }

    public String getValue(){
        return this.value;
    }
    
	public String getDesc() {
		return desc;
	}
	
	@Override
	public String toString() {
		return this.getValue();
	}
	
}
