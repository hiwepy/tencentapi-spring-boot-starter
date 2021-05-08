package com.tencentcloud.spring.boot.tim;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.tencentcloud.spring.boot.TencentTimProperties;
import com.tencentcloud.spring.boot.tim.resp.ApiResponse;
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

	private final TencentTimAccountAsyncOperations accountOps = new TencentTimAccountAsyncOperations(this);
	private final TencentTimOpenimAsyncOperations imOps = new TencentTimOpenimAsyncOperations(this);
	private final TencentTimProfileAsyncOperations profileOps = new TencentTimProfileAsyncOperations(this);
	private final TencentTimSnsAsyncOperations snsOps = new TencentTimSnsAsyncOperations(this);
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
								return tlsSigAPIv2.genUserSig(timProperties.getIdentifier(), timProperties.getExpire());
							}
							
						});
		
	}

	public TencentTimAccountAsyncOperations opsForAccount() {
		return accountOps;
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
		log.info("im参数   {}", pathParams);
		return pathParams;
	}
	
	public <T> T readValue(String json, Class<T> cls) {
		try {
			return objectMapper.readValue(json, cls);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> T requestInvoke(String url, Object params, Class<T> cls) {
		Optional<T> optional = this.requestInvoke(url, params, (start, response) -> {
			if (response.isSuccessful()) {
				try {
					String body = response.body().string();
					log.debug("Request Success: code : {}, body : {} , use time : {} ", response.code(), body , System.currentTimeMillis() - start);
					return objectMapper.readValue(body, cls);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
			return null;
		});
		return optional.isPresent() ? optional.get() : null;
	}
	
	public <T> Optional<T> requestInvoke(String url, Object params, BiFunction<Long, Response, T> function ) {
		long start = System.currentTimeMillis();
		try {
			RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_VALUE),
					objectMapper.writeValueAsString(params));
			Request request = new Request.Builder().url(url).post(requestBody).build();
			try(Response response = okhttp3Client.newCall(request).execute();) {
				log.debug("Request Success: code : {}, use time : {} ", response.code(), System.currentTimeMillis() - start);
				if (response.isSuccessful()) {
					return Optional.ofNullable(function.apply(start, response));
	            }
			}
		} catch (Exception e) {
			log.error("Request Failure : {}, use time : {} ", e.getMessage(), System.currentTimeMillis() - start);
		}
		return Optional.empty();
	}
	
	public <T extends ApiResponse> void requestAsyncInvoke(String url, Object params, Class<T> cls, Consumer<T> consumer) {
		this.requestAsyncInvoke(url, params, (response) -> {
			if (response.isSuccessful()) {
				try {
					String body = response.body().string();
					T res = objectMapper.readValue(body, cls);
					if (res.isSuccess()) {
						log.debug("Request Success, ActionStatus : {}, Body : {}", res.getActionStatus(), body);
					} else {
						log.error("Request Failure, ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
					}
					consumer.accept(res);
				} catch (IOException e) {
					log.error(e.getMessage());
				}
            }
		});
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
