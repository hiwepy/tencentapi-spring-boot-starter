package com.tencentcloud.spring.boot.tim;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.tencentcloud.spring.boot.TencentTimProperties;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;
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
public class TencentTimTemplate implements InitializingBean {

	public final static String APPLICATION_JSON_VALUE = "application/json";
	public final static String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
	public final static MediaType APPLICATION_JSON = MediaType.parse(APPLICATION_JSON_VALUE);
	public final static MediaType APPLICATION_JSON_UTF8 = MediaType.parse(APPLICATION_JSON_UTF8_VALUE);

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
	private TimUserIdProvider timUserIdProvider;

	private final TencentTimAccountAsyncOperations accountOps = new TencentTimAccountAsyncOperations(this);
	private final TencentTimAllMemberPushAsyncOperations pushOps = new TencentTimAllMemberPushAsyncOperations(this);
	private final TencentTimGroupAsyncOperations groupOps = new TencentTimGroupAsyncOperations(this);
	private final TencentTimNospeakingAsyncOperations noSpeakingOps = new TencentTimNospeakingAsyncOperations(this);
	private final TencentTimOpenimAsyncOperations imOps = new TencentTimOpenimAsyncOperations(this);
	private final TencentTimProfileAsyncOperations profileOps = new TencentTimProfileAsyncOperations(this);
	private final TencentTimSnsAsyncOperations snsOps = new TencentTimSnsAsyncOperations(this);
	private LoadingCache<String, String> tlsSigCache;

	@Override
	public void afterPropertiesSet() throws Exception {
		// 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public TencentTimTemplate(TencentTimProperties timProperties, OkHttpClient okhttp3Client, TimUserIdProvider timUserIdProvider) {
		this(timProperties, new TLSSigAPIv2(timProperties.getSdkappid(), timProperties.getPrivateKey()),
				okhttp3Client, timUserIdProvider);
	}

	public TencentTimTemplate(TencentTimProperties timProperties, TLSSigAPIv2 tlsSigAPIv2,
			OkHttpClient okhttp3Client, TimUserIdProvider timUserIdProvider) {
		this.timProperties = timProperties;
		this.tlsSigAPIv2 = tlsSigAPIv2;
		this.okhttp3Client = okhttp3Client;
		this.timUserIdProvider = timUserIdProvider;
		this.tlsSigCache = CacheBuilder.newBuilder()
						.expireAfterWrite(Duration.ofSeconds(Math.max(timProperties.getExpire() - 60, 60)))
						.build(new CacheLoader<String, String>() {

							@Override
							public String load(String key) throws Exception {
								return tlsSigAPIv2.genUserSig(timProperties.getIdentifier(), timProperties.getExpire());
							}

						});

	}

	public TencentTimAccountAsyncOperations opsForAccount() {
		return accountOps;
	}

	public TencentTimAllMemberPushAsyncOperations opsForPush() {
		return pushOps;
	}

	public TencentTimGroupAsyncOperations opsForGroup() {
		return groupOps;
	}

	public TencentTimNospeakingAsyncOperations opsForNoSpeaking() {
		return noSpeakingOps;
	}

	public TencentTimOpenimAsyncOperations opsForOpenim() {
		return imOps;
	}

	public TencentTimProfileAsyncOperations opsForProfile() {
		return profileOps;
	}

	public TencentTimSnsAsyncOperations opsForSns() {
		return snsOps;
	}

	public String genUserSig(String identifier) {
		return tlsSigAPIv2.genUserSig(identifier, timProperties.getExpire());
	}

	public String genUserSig(String identifier, long expire) {
		return tlsSigAPIv2.genUserSig(identifier, expire);
	}

	public TLSSigAPIv2 getTlsSigAPIv2() {
		return tlsSigAPIv2;
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
		return pathParams;
	}

	public <T> T readValue(String json, Class<T> cls) {
		try {
			return JSONObject.parseObject(json, cls);
			//return objectMapper.readValue(json, cls);
		} catch (Exception e) {
			log.error(e.getMessage());
			return BeanUtils.instantiateClass(cls);
		}
	}

	public <T extends TimActionResponse> T requestInvoke(String url, Object params, Class<T> cls) {
		long start = System.currentTimeMillis();
		T res = null;
		try {

			String paramStr = objectMapper.writeValueAsString(params);
			log.info("Tim Request Param :  {}", paramStr);

			RequestBody requestBody = RequestBody.create(APPLICATION_JSON_UTF8, paramStr);
			Request request = new Request.Builder().url(url).post(requestBody).build();

			try(Response response = okhttp3Client.newCall(request).execute();) {
				if (response.isSuccessful()) {
					String body = response.body().string();
					log.info("Tim Request Success : url : {}, params : {}, code : {}, body : {} , use time : {} ", url, params, response.code(), body , System.currentTimeMillis() - start);
					res = this.readValue(body, cls);
	            } else {
	            	log.error("Tim Request Failure : url : {}, params : {}, code : {}, message : {}, use time : {} ", url, params, response.code(), response.message(), System.currentTimeMillis() - start);
	            	res = BeanUtils.instantiateClass(cls);
				}
			}
		} catch (Exception e) {
			log.error("Tim Request Error : url : {}, params : {}, use time : {} ,  {}", url, params, e.getMessage(), System.currentTimeMillis() - start);
			res = BeanUtils.instantiateClass(cls);
		}
		return res;
	}

	public void requestAsyncInvoke(String url, Object params, Consumer<Response> consumer) {

		long start = System.currentTimeMillis();

		try {

			String paramStr = objectMapper.writeValueAsString(params);
			log.info("Tim Request Param :  {}", paramStr);

			RequestBody requestBody = RequestBody.create(APPLICATION_JSON_UTF8, paramStr);
			Request request = new Request.Builder().url(url).post(requestBody).build();
			okhttp3Client.newCall(request).enqueue(new Callback() {

	            @Override
	            public void onFailure(Call call, IOException e) {
	            	log.error("Tim Async Request Failure : url : {}, params : {}, message : {}, use time : {} ", url, params, e.getMessage(), System.currentTimeMillis() - start);
	            }

	            @Override
	            public void onResponse(Call call, Response response) {
                	if (response.isSuccessful()) {
    					log.info("Tim Async Request Success : url : {}, params : {}, code : {}, message : {} , use time : {} ", url, params, response.code(), response.message(), System.currentTimeMillis() - start);
    					consumer.accept(response);
                    } else {
                    	log.error("Tim Async Request Failure : url : {}, params : {}, code : {}, message : {}, use time : {} ", url, params, response.code(), response.message(), System.currentTimeMillis() - start);
        			}
	            }

	        });
		} catch (Exception e) {
			log.error("Tim Async Request Error : url : {}, params : {}, message : {} , use time : {} ", url, params, e.getMessage(), System.currentTimeMillis() - start);
		}
	}

	public String getUserIdByImUser(String account) {
		return timUserIdProvider.getUserIdByImUser(timProperties.getSdkappid(), account);
	}

	public String getImUserByUserId(String userId) {
		return timUserIdProvider.getImUserByUserId(timProperties.getSdkappid(), userId);
	}
}
