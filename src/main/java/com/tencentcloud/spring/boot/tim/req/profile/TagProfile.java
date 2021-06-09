package com.tencentcloud.spring.boot.tim.req.profile;

import java.util.ArrayList;
import java.util.List;

/**
 * https://cloud.tencent.com/document/product/269/1500#.E6.A0.87.E9.85.8D.E8.B5.84.E6.96.99.E5.AD.97.E6.AE.B5
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 */
public enum TagProfile {

	Tag_Profile_IM_Nick("Tag_Profile_IM_Nick", "昵称", "长度不得超过500个字节"),
	Tag_Profile_IM_Gender("Tag_Profile_IM_Gender", "性别", "Gender_Type_Unknown：没设置性别、Gender_Type_Female：女性、Gender_Type_Male：男性"),
	Tag_Profile_IM_BirthDay("Tag_Profile_IM_BirthDay", "生日", "推荐用法：20190419"),
	Tag_Profile_IM_Location("Tag_Profile_IM_Location", "所在地", "长度不得超过16个字节，推荐用法如下：\r\n" + 
			"App 本地定义一套数字到地名的映射关系\r\n" + 
			"后台实际保存的是4个 uint32_t 类型的数字\r\n" + 
			"其中第一个 uint32_t 表示国家\r\n" + 
			"第二个 uint32_t 用于表示省份\r\n" + 
			"第三个 uint32_t 用于表示城市\r\n" + 
			"第四个 uint32_t 用于表示区县 "),
	Tag_Profile_IM_SelfSignature("Tag_Profile_IM_SelfSignature", "个性签名", "长度不得超过500个字节"),
	Tag_Profile_IM_AllowType("Tag_Profile_IM_AllowType", "加好友验证方式", "AllowType_Type_NeedConfirm：需要经过自己确认对方才能添加自己为好友\r\n" + 
			"AllowType_Type_AllowAny：允许任何人添加自己为好友\r\n" + 
			"AllowType_Type_DenyAny：不允许任何人添加自己为好友 "),
	Tag_Profile_IM_Language("Tag_Profile_IM_Language", "语言", "无"),
	Tag_Profile_IM_Image("Tag_Profile_IM_Image", "头像URL", "长度不得超过500个字节"),
	Tag_Profile_IM_MsgSettings("Tag_Profile_IM_MsgSettings", "消息设置", "标志位：Bit0：置0表示接收消息，置1则不接收消息 "),
	Tag_Profile_IM_AdminForbidType("Tag_Profile_IM_AdminForbidType", "管理员禁止加好友标识", "长度不得超过500个字节"),
	Tag_Profile_IM_Level("Tag_Profile_IM_Level", "等级", "通常一个 UINT-8 数据即可保存一个等级信息，您可以考虑拆分保存，从而实现多种角色的等级信息"),
	Tag_Profile_IM_Role("Tag_Profile_IM_Role", "角色", "通常一个 UINT-8 数据即可保存一个角色信息，您可以考虑拆分保存，从而保存多种角色信息"),
	;
	
    private String value;
    private String desc;
    private String ramark;

    TagProfile(String value,String desc, String ramark){
        this.value = value;
        this.desc = desc;
        this.ramark = ramark;
    }

    public String getValue(){
        return this.value;
    }
    
	public String getDesc() {
		return desc;
	}
	
	public String getRamark() {
		return ramark;
	}
	
	public static List<String> asTagList() {
		List<String> tagList = new ArrayList<String>();
		for (TagProfile tag : TagProfile.values()) {
			tagList.add(tag.getValue());
		}
		return tagList;
	}
	
}
