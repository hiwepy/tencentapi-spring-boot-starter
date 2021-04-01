package com.tencentcloud.spring.boot.tim;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.tencentcloud.spring.boot.TencentTimProperties;
import com.tencentyun.TLSSigAPIv2;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

	private TencentTimProperties timProperties;
	private TLSSigAPIv2 tlsSigAPIv2;
	private OkHttpClient okhttp3Client;

	private final TencentTimAccountOperations accountOps = new TencentTimAccountOperations(this);
	private final TencentTimOpenimOperations imOps = new TencentTimOpenimOperations(this);
	private final TencentTimProfileOperations profileOps = new TencentTimProfileOperations(this);
	private final TencentTimSnsOperations snsOps = new TencentTimSnsOperations(this);
	private LoadingCache<String, String> tlsSigCache;
	
	public TencentTimTemplate(TencentTimProperties timProperties, OkHttpClient okhttp3Client) {
		this(timProperties, new TLSSigAPIv2(timProperties.getSdkappid(), timProperties.getPrivateKey()),
				okhttp3Client);
	}

	public TencentTimTemplate(TencentTimProperties timProperties, TLSSigAPIv2 tlsSigAPIv2,
			OkHttpClient okhttp3Client) {
		this.timProperties = timProperties;
		this.tlsSigAPIv2 = tlsSigAPIv2;
		this.okhttp3Client = okhttp3Client;
		this.tlsSigCache = CacheBuilder.newBuilder()
						.expireAfterWrite(Duration.ofSeconds(Math.max(timProperties.getExpire() - 60, 60)))
						.build(new CacheLoader<String, String>() {

							@Override
							public String load(String key) throws Exception {
								return tlsSigAPIv2.genSig(timProperties.getIdentifier(), timProperties.getExpire());
							}
							
						});
		
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
		return tlsSigAPIv2.genSig(identifier, timProperties.getExpire());
	}

	public String genSig(String identifier, long expire) {
		return tlsSigAPIv2.genSig(identifier, expire);
	}

	public String genSigWithUserBuf(String identifier, long expire, byte[] userbuf) {
		return tlsSigAPIv2.genSigWithUserBuf(identifier, expire, userbuf);
	}

	public long getMsgLifeTime() {
		return timProperties.getMsgLifeTime();
	}
	
	public Map<String, String> getDefaultParams() {
		Map<String, String> pathParams = Maps.newHashMap();
		pathParams.put(USER_SIG, tlsSigCache.getUnchecked(USER_SIG));
		pathParams.put(IDENTIFIER, timProperties.getIdentifier());
		pathParams.put(SDKAPPID, timProperties.getSdkappid().toString());
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

	public String requestInvoke(String url, Object params) {
		long start = System.currentTimeMillis();
		String content = null;
		try {
			RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_VALUE),
					objectMapper.writeValueAsString(params));
			Request request = new Request.Builder().url(url).post(requestBody).build();
			Response response = okhttp3Client.newCall(request).execute();
			if (response.isSuccessful()) {
                content = response.body().string();
                log.debug("Request Success: code : {}, body : {} , use time : {} ", response.code(), content , System.currentTimeMillis() - start);
                return content;
            }
		} catch (Exception e) {
			log.error("Request Failure : {}, use time : {} ", e.getMessage(), System.currentTimeMillis() - start);
		}
		return content;
	}
	
	public void requestAsyncInvoke(String url, Object params, Consumer<Response> consumer) {
		long start = System.currentTimeMillis();
		try {
			
			RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_VALUE),
					objectMapper.writeValueAsString(params));
			Request request = new Request.Builder().url(url).post(requestBody).build();
			okhttp3Client.newCall(request).enqueue(new Callback() {
				
                @Override
                public void onFailure(Call call, IOException e) {
                	log.error("Request Failure : {}, use time : {} ", e.getMessage(), System.currentTimeMillis() - start);
                }
                
                @Override
                public void onResponse(Call call, Response response) {
                    try {
                    	log.debug("Request Success: code : {}, , use time : {} ", response.code(), System.currentTimeMillis() - start);
                    	consumer.accept(response); 
					} finally {
						response.close();
					}
                }
                
            });
		} catch (Exception e) {
			log.error("请求异常", e);
		}
	}
	

	public String getUserIdByImUser(String imUser) {
		if (!StringUtils.isNumeric(imUser)) {
		}
		return imUser;
	}

	public String getImUserByUserId(String userId) {
		return String.valueOf(userId);
	}

}
