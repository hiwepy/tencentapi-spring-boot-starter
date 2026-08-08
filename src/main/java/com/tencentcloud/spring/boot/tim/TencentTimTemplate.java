package com.tencentcloud.spring.boot.tim;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import lombok.Getter;
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
 * Core facade for the Tencent Cloud IM (TIM) REST API.
 * <p>
 * Wraps a shared {@link OkHttpClient} and a {@link TLSSigAPIv2} user-signature
 * generator. The admin user signature is cached (refreshed shortly before
 * expiry) and reused for every request. Domain-specific operations are exposed
 * through grouped async operation helpers accessible via the {@code *Ops}
 * getters (account, push, group, no-speaking, openim, profile, sns).</p>
 * <p>See the
 * <a href="https://cloud.tencent.com/document/product/269/42440">TIM REST API overview</a>.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Slf4j
public class TencentTimTemplate implements InitializingBean {

	/** HTTP {@code Content-Type} for plain JSON. */
	public final static String APPLICATION_JSON_VALUE = "application/json";
	/** HTTP {@code Content-Type} for UTF-8 JSON. */
	public final static String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
	/** OkHttp {@link MediaType} for plain JSON. */
	public final static MediaType APPLICATION_JSON = MediaType.parse(APPLICATION_JSON_VALUE);
	/** OkHttp {@link MediaType} for UTF-8 JSON. */
	public final static MediaType APPLICATION_JSON_UTF8 = MediaType.parse(APPLICATION_JSON_UTF8_VALUE);

	private static final String USER_SIG = "usersig";
	private static final String IDENTIFIER = "identifier";
	private static final String SDKAPPID = "sdkappid";
	private static final String RANDOM = "random";
	private static final String CONTENTTYPE = "contenttype";
	private static final String CONTENTTYPE_JSON = "json";

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final TencentTimProperties timProperties;
	@Getter
    private TLSSigAPIv2 tlsSigAPIv2;
	private final OkHttpClient okhttp3Client;
	private final TimInfoProvider timInfoProvider;

	/** Async account-management operations (import / delete / kick, etc.). */
	@Getter
	private final TencentTimAccountAsyncOperations accountOps = new TencentTimAccountAsyncOperations(this);
	/** Async all-member push operations. */
	@Getter
	private final TencentTimAllMemberPushAsyncOperations pushOps = new TencentTimAllMemberPushAsyncOperations(this);
	/** Async group operations. */
	@Getter
	private final TencentTimGroupAsyncOperations groupOps = new TencentTimGroupAsyncOperations(this);
	/** Async no-speaking (mute) operations. */
	@Getter
	private final TencentTimNospeakingAsyncOperations noSpeakingOps = new TencentTimNospeakingAsyncOperations(this);
	/** Async open-instant-messaging operations. */
	@Getter
	private final TencentTimOpenimAsyncOperations imOps = new TencentTimOpenimAsyncOperations(this);
	/** Async user profile operations. */
	@Getter
	private final TencentTimProfileAsyncOperations profileOps = new TencentTimProfileAsyncOperations(this);
	/** Async social-network (friend) operations. */
	@Getter
	private final TencentTimSnsAsyncOperations snsOps = new TencentTimSnsAsyncOperations(this);
	private final LoadingCache<String, String> tlsSigCache;

	/**
	 * Lifecycle hook invoked by Spring after properties are set; configures the
	 * Jackson {@link ObjectMapper} to serialize all accessors and to ignore
	 * unknown properties.
	 *
	 * @throws Exception never thrown by the current implementation
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// Serialize all accessors (field/get/set) regardless of visibility.
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * Creates a template that builds its own {@link TLSSigAPIv2} from the bound
	 * SdkAppid and private key.
	 *
	 * @param timProperties    bound TIM configuration
	 * @param okhttp3Client    shared HTTP client used for REST calls
	 * @param timInfoProvider  user-id/account translator
	 */
	public TencentTimTemplate(TencentTimProperties timProperties, OkHttpClient okhttp3Client, TimInfoProvider timInfoProvider) {
		this(timProperties, new TLSSigAPIv2(timProperties.getSdkappid(), timProperties.getPrivateKey()),
				okhttp3Client, timInfoProvider);
	}

	/**
	 * Creates a template with an explicit {@link TLSSigAPIv2} instance.
	 *
	 * @param timProperties    bound TIM configuration
	 * @param tlsSigAPIv2      pre-built user-signature generator
	 * @param okhttp3Client    shared HTTP client used for REST calls
	 * @param timInfoProvider  user-id/account translator
	 */
	public TencentTimTemplate(TencentTimProperties timProperties, TLSSigAPIv2 tlsSigAPIv2,
			OkHttpClient okhttp3Client, TimInfoProvider timInfoProvider) {
		this.timProperties = timProperties;
		this.tlsSigAPIv2 = tlsSigAPIv2;
		this.okhttp3Client = okhttp3Client;
		this.timInfoProvider = timInfoProvider;
		this.tlsSigCache = CacheBuilder.newBuilder()
						.expireAfterWrite(Duration.ofSeconds(Math.max(timProperties.getExpire() - 60, 60)))
						.build(new CacheLoader<String, String>() {

							@Override
							public String load(String key) throws Exception {
								return tlsSigAPIv2.genUserSig(timProperties.getIdentifier(), timProperties.getExpire());
							}

						});

	}

	/**
	 * Generates a user signature for the given identifier using the configured
	 * default expiry.
	 *
	 * @param identifier the TIM account identifier
	 * @return the generated UserSig
	 */
	public String genUserSig(String identifier) {
		return tlsSigAPIv2.genUserSig(identifier, timProperties.getExpire());
	}

	/**
	 * Generates a user signature for the given identifier with a custom expiry.
	 *
	 * @param identifier the TIM account identifier
	 * @param expire     signature validity in seconds
	 * @return the generated UserSig
	 */
	public String genUserSig(String identifier, long expire) {
		return tlsSigAPIv2.genUserSig(identifier, expire);
	}

	/**
	 * @return the configured message lifetime (seconds).
	 */
    public long getMsgLifeTime() {
		return timProperties.getMsgLifeTime();
	}

	/**
	 * Builds the default TIM REST API query parameters (UserSig, identifier,
	 * SdkAppid, random, contenttype) used by every request.
	 *
	 * @return an unmodifiable map of query parameter name to value
	 */
	public Map<String, String> getDefaultParams() {
		Map<String, String> pathParams = Maps.newHashMap();
		pathParams.put(USER_SIG, tlsSigCache.getUnchecked(USER_SIG));
		pathParams.put(IDENTIFIER, timProperties.getIdentifier());
		pathParams.put(SDKAPPID, timProperties.getSdkappid().toString());
		pathParams.put(RANDOM, UUID.randomUUID().toString().replace("-", "").toLowerCase());
		pathParams.put(CONTENTTYPE, CONTENTTYPE_JSON);
		return pathParams;
	}

	/**
	 * Deserializes a JSON string into the given type, returning a default
	 * instance of the type on failure rather than throwing.
	 *
	 * @param json the JSON payload
	 * @param cls  the target type
	 * @param <T>  the target type
	 * @return the deserialized value, or a new default instance on error
	 */
	public <T> T readValue(String json, Class<T> cls) {
		try {
			return objectMapper.readValue(json, cls);
		} catch (Exception e) {
			log.error(e.getMessage());
			return BeanUtils.instantiateClass(cls);
		}
	}

	/**
	 * Synchronously POSTs the given parameters as JSON to {@code url} and
	 * deserializes the response into the requested type.
	 *
	 * @param url    the fully-qualified TIM REST API URL
	 * @param params the request body object (serialized to JSON)
	 * @param cls    the response type
	 * @param <T>    the response type, extending {@link TimActionResponse}
	 * @return the deserialized response, or a default instance on failure
	 */
	public <T extends TimActionResponse> T requestInvoke(String url, Object params, Class<T> cls) {
		long start = System.currentTimeMillis();
		T res;
		try {

			String paramStr = objectMapper.writeValueAsString(params);
			log.info("Tim Request Invoke Param :  {}", paramStr);

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

	/**
	 * Asynchronously POSTs the given parameters as JSON to {@code url}, invoking
	 * the supplied callback only on a successful (2xx) response. Failures are
	 * logged and swallowed.
	 *
	 * @param url      the fully-qualified TIM REST API URL
	 * @param params   the request body object (serialized to JSON)
	 * @param consumer callback invoked with the successful OkHttp {@link Response}
	 */
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

	/**
	 * Resolves the application user id for a given TIM account, delegating to
	 * the {@link TimInfoProvider}.
	 *
	 * @param account the TIM account identifier
	 * @return the application user id
	 */
	public String getUserIdByImUser(String account) {
		return timInfoProvider.getUserIdByImUser(timProperties.getSdkappid(), account);
	}

	/**
	 * Resolves the TIM account for a given application user id, delegating to
	 * the {@link TimInfoProvider}.
	 *
	 * @param userId the application user id
	 * @return the TIM account identifier
	 */
	public String getImUserByUserId(String userId) {
		return timInfoProvider.getImUserByUserId(timProperties.getSdkappid(), userId);
	}
}
