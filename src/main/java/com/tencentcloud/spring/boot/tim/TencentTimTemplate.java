package com.tencentcloud.spring.boot.tim;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.tencentcloud.spring.boot.TencentTimProperties;
import com.tencentyun.TLSSigAPIv2;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Tim 接口集成 https://cloud.tencent.com/document/product/269/42440
 */
@Slf4j
public class TencentTimTemplate {

	public final static String APPLICATION_JSON_VALUE = "application/json";
	public final static String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

	private static final String USER_SIG = "usersig";
	private static final String IDENTIFIER = "identifier";
	private static final String SDKAPPID = "sdkappid";
	private static final String RANDOM = "random";
	private static final String CONTENTTYPE = "contenttype";
	private static final String CONTENTTYPE_JSON = "json";

	private final ObjectMapper objectMapper = new ObjectMapper();

	private String identifier;
	private Long sdkappid;
	private long expire;
	private String sign;
	private TLSSigAPIv2 tlsSigAPIv2;
	private OkHttpClient okhttp3Client;

	private final TencentTimAccountOperations accountOps = new TencentTimAccountOperations(this);
	private final TencentTimOpenimOperations imOps = new TencentTimOpenimOperations(this);
	private final TencentTimProfileOperations profileOps = new TencentTimProfileOperations(this);
	private final TencentTimSnsOperations snsOps = new TencentTimSnsOperations(this);

	public TencentTimTemplate(TencentTimProperties webimProperties, OkHttpClient okhttp3Client) {
		this(webimProperties, new TLSSigAPIv2(webimProperties.getSdkappid(), webimProperties.getPrivateKey()),
				okhttp3Client);
	}

	public TencentTimTemplate(TencentTimProperties webimProperties, TLSSigAPIv2 tlsSigAPIv2,
			OkHttpClient okhttp3Client) {
		this.sdkappid = webimProperties.getSdkappid();
		this.identifier = webimProperties.getIdentifier();
		this.expire = webimProperties.getExpire();
		this.tlsSigAPIv2 = tlsSigAPIv2;
		this.okhttp3Client = okhttp3Client;
		this.sign = this.genSig(webimProperties.getIdentifier(), webimProperties.getExpire());

	}

	public TencentTimAccountOperations opsForAccount() {
		return accountOps;
	}

	public TencentTimOpenimOperations opsForOpenim() {
		return imOps;
	}

	public TencentTimProfileOperations opsForProfile() {
		return profileOps;
	}

	public TencentTimSnsOperations opsForSns() {
		return snsOps;
	}

	public String genSig(String identifier) {
		return tlsSigAPIv2.genSig(identifier, expire);
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
	public Map<String, String> getDefaultParams() {
		Map<String, String> pathParams = Maps.newHashMap();
		pathParams.put(USER_SIG, sign);
		pathParams.put(IDENTIFIER, identifier);
		pathParams.put(SDKAPPID, sdkappid.toString());
		pathParams.put(RANDOM, UUID.randomUUID().toString().replace("-", "").toLowerCase());
		pathParams.put(CONTENTTYPE, CONTENTTYPE_JSON);
		log.info("im参数   {}", pathParams);
		return pathParams;
	}

	public <T> T request(String url, Object params, Class<T> cls) {
		return toBean(requestInvoke(url, params), cls);
	}

	public <T> T toBean(String json, Class<T> cls) {
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
	public String requestInvoke(String url, Object params) {
		String json = null;
		try {
			RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_VALUE),
					objectMapper.writeValueAsString(params));
			Request request = new Request.Builder().url(url).post(requestBody).build();
			json = okhttp3Client.newCall(request).execute().body().string();
			log.info("IM 响应 {}", json);
		} catch (Exception e) {
			log.error("IM 请求异常", e);
		}
		return json;
	}

	/**
	 * 根据im用户id获取用户id
	 * 
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
	 * 
	 * @param userId
	 * @return
	 */
	public String getImUserByUserId(String userId) {
		return String.valueOf(userId);
	}

}
