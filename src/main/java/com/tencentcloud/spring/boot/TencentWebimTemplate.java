package com.tencentcloud.spring.boot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.tencentcloud.spring.boot.tim.IMRequestAddress;
import com.tencentcloud.spring.boot.tim.req.message.BlacklistMessage;
import com.tencentcloud.spring.boot.tim.resp.BlacklistResponse;
import com.tencentcloud.spring.boot.tim.resp.IMActionResponse;
import com.tencentcloud.spring.boot.tim.resp.UserProfileItemResponse;
import com.tencentyun.TLSSigAPIv2;

import lombok.extern.slf4j.Slf4j;

/**
 * Tim 接口集成 1、帐号管理
 * 
 * 
 */
@Slf4j
public class TencentWebimTemplate {
	
	private static final String DELIMITER = "&";
    private static final String SEPARATOR = "=";
    private static final String USER_SIG = "usersig";
    private static final String IDENTIFIER = "identifier";
    private static final String SDKAPPID = "sdkappid";
    private static final String RANDOM = "random";
    private static final String CONTENTTYPE = "contenttype";
    private static final String CONTENTTYPE_JSON = "json";
	private OkHttpClient okhttp3Client;

	/**
	 * 封装请求参数
	 */
	private final Joiner.MapJoiner joiner = Joiner.on(DELIMITER).withKeyValueSeparator(SEPARATOR);

	private final ObjectMapper objectMapper = new ObjectMapper();

	private String prefix = "https://console.tim.qq.com";
	private TLSSigAPIv2 tlsSigAPIv2;

	public TencentWebimTemplate(TLSSigAPIv2 tlsSigAPIv2, OkHttpClient okhttp3Client) {
		this.tlsSigAPIv2 = tlsSigAPIv2;
		this.okhttp3Client = okhttp3Client;
	}

	public String genSig(String identifier, long expire) {
		return tlsSigAPIv2.genSig(identifier, expire);
	}

	public String genSigWithUserBuf(String identifier, long expire, byte[] userbuf) {
		return tlsSigAPIv2.genSigWithUserBuf(identifier, expire, userbuf);
	}

	
	 /**
     * 返回默认的参数
     *
     * @return
     */
    private Map<String, String> getDefaultParams() {
        Map<String, String> pathParams = Maps.newHashMap();
        pathParams.put(USER_SIG, USER_SIGN);
        pathParams.put(IDENTIFIER, ADMINISTRATOR);
        pathParams.put(SDKAPPID, SDK_APP_ID.toString());
        pathParams.put(RANDOM, UUID.randomUUID().toString().replace("-", "").toLowerCase());
        pathParams.put(CONTENTTYPE, CONTENTTYPE_JSON);
        log.info("im参数   {}",pathParams);
        return pathParams;
    }

    private <T> T request(String url, Object params, Class<T> cls) {
        return toBean(requestInvoke(url, params), cls);
    }

    private <T> T toBean(String json, Class<T> cls) {
        try {
            return objectMapper.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * http 请求service
     *
     * @param url
     * @param params
     * @return
     */
    private String requestInvoke(String url, Object params) {
        String json = null;
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(params));
            Request request = new Request.Builder().url(url).post(requestBody).build();
            json = okhttp3Client.newCall(request).execute().body().string();
            log.info("IM 响应 {}",json);
        } catch (Exception e) {
            log.error("IM 请求异常",e);
        }
        return json;
    }

    /**
     * 1、导入单个帐号 v4/im_open_login_svc/account_import
     *
     * @param userId
     * @param nickname
     * @param avatar
     * @return
     */
    public IMActionResponse accountImport(String userId, String nickname, String avatar) {
        Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
                .put("Identifier", getImUserByUserId(userId))
                .put("Nick", nickname)
                .put("FaceUrl", avatar)
                .build();
        IMActionResponse res = request(IMRequestAddress.ACCOUNT_IMPORT.getValue() + joiner.join(getDefaultParams()), requestBody, IMActionResponse.class);
        System.out.println(res);
        if (!res.isSuccess()) {
            log.error("导入信息失败, response message is: {}", res);
        }
        return res;
    }

    public UserProfileItemResponse portraitGet(Long userId) {
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.add("Tag_Profile_IM_Nick");
        tagList.add("Tag_Profile_IM_Image");
        ArrayList<String> toAccount = new ArrayList<String>();
        toAccount.add(String.valueOf(userId));
        Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
                .put("To_Account", toAccount)
                .put("TagList", tagList)
                .build();
        UserProfileItemResponse res = request(IMRequestAddress.PORTRAIT_GET.getValue() + joiner.join(getDefaultParams()), requestBody, UserProfileItemResponse.class);
        if (!res.isSuccess()) {
            log.error("获取信息失败, response message is: {}", res);
        }
        return res;
    }

    /**
     * 设置资料
     *
     * @param userId
     * @param nickname
     * @param avatar
     * @return
     */
    public IMActionResponse portraitSet(Long userId, String nickname, String avatar) {
        IMActionResponse res = new IMActionResponse();
        if (userId == null || userId <= 0 ){
            return res;
        }
        if (StringUtils.isBlank(nickname) && StringUtils.isBlank(avatar)){
            return res;
        }
        ArrayList<HashMap<String, String>> objects = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Tag", "Tag_Profile_IM_Nick");
        hashMap.put("Value", nickname);
        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("Tag", "Tag_Profile_IM_Image");
        hashMap1.put("Value", avatar);
        objects.add(hashMap);
        objects.add(hashMap1);

        Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
                .put("From_Account", String.valueOf(userId))
                .put("ProfileItem", objects)
                .build();
        res = request(IMRequestAddress.PORTRAIT_SET.getValue() + joiner.join(getDefaultParams()), requestBody, IMActionResponse.class);
        if (!res.isSuccess()) {
            log.error("设置资料失败, response message is: {}", res);
        }
        return res;
    }

    public Boolean addBlackList(String fromAccount, String toAccount){
        BlacklistMessage message = new BlacklistMessage();
        message.setFromAccount(fromAccount);
        message.setToAccount(Arrays.asList(toAccount));

        BlacklistResponse request = request(IMRequestAddress.BLACK_LIST_ADD.getValue() + joiner.join(getDefaultParams()), message, BlacklistResponse.class);
        if (!request.isSuccess()) {
            log.error("拉黑失败, response message is: {}", request);
            return false;
        }
        return true;
    }

    public Boolean deleteBlackList(String fromAccount, String toAccount){
        BlacklistMessage message = new BlacklistMessage();
        message.setFromAccount(fromAccount);
        message.setToAccount(Arrays.asList(toAccount));

        BlacklistResponse request = request(IMRequestAddress.BLACK_LIST_DELETE.getValue() + joiner.join(getDefaultParams()),message,BlacklistResponse.class);
        if (!request.isSuccess()) {
            log.error("取消拉黑失败, response message is: {}", request);
            return false;
        }
        return true;
    }

    public BlacklistResponse getBlackList(String fromAccount, Integer lastSequence){
        BlacklistMessage message = new BlacklistMessage();
        message.setFromAccount(fromAccount);
        message.setStartIndex(0);
        message.setMaxLimited(20);
        message.setLastSequence(lastSequence);

        BlacklistResponse blacklistResponse = request(IMRequestAddress.BLACK_LIST_GET.getValue() + joiner.join(getDefaultParams()), message, BlacklistResponse.class);
        return blacklistResponse;
    }
    
    /**
     * 根据im用户id获取用户id
     * @param imUser
     * @return
     */
    public String getUserIdByImUser(String imUser) {
        if (!StringUtils.isNumeric(imUser)) {
        }
        return imUser;
    }

    /**
     * 根据用户id获取im用户id
     * @param userId
     * @return
     */
    public String getImUserByUserId(String userId) {
        return String.valueOf(userId);
    }

}
