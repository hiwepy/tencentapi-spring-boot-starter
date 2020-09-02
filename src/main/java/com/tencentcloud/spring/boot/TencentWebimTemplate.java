package com.tencentcloud.spring.boot;

import com.tencentyun.TLSSigAPIv2;

/**
 * Tim 接口集成
 * 1、帐号管理
 * 
 * 
 */
public class TencentWebimTemplate {

	private String prefix = "https://console.tim.qq.com";
	private TLSSigAPIv2 tlsSigAPIv2;
	
	public TencentWebimTemplate(TLSSigAPIv2 tlsSigAPIv2) {
		this.tlsSigAPIv2 = tlsSigAPIv2;
	}
	
    public String genSig(String identifier, long expire){
       return tlsSigAPIv2.genSig(identifier, expire);
    }
    
    public String genSigWithUserBuf(String identifier, long expire, byte[] userbuf) {
    	 return tlsSigAPIv2.genSigWithUserBuf(identifier, expire, userbuf);
    }
    
    /**
     * 1、导入单个帐号 	v4/im_open_login_svc/account_import
     * @return
     */
    public boolean accountImport() {
    	
    }
    
    
	
}
