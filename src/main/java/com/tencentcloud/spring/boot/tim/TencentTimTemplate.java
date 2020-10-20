package com.tencentcloud.spring.boot.tim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.tencentcloud.spring.boot.TencentTimProperties;
import com.tencentcloud.spring.boot.tim.req.message.BlacklistMessage;
import com.tencentcloud.spring.boot.tim.resp.AccountCheckActionResponse;
import com.tencentcloud.spring.boot.tim.resp.BlacklistResponse;
import com.tencentcloud.spring.boot.tim.resp.IMActionResponse;
import com.tencentcloud.spring.boot.tim.resp.UserProfileItemResponse;
import com.tencentyun.TLSSigAPIv2;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Tim 接口集成 1、帐号管理
 * https://cloud.tencent.com/document/product/269/42440
 */
@Slf4j
public class TencentTimTemplate {

	public static final String PREFIX = "https://console.tim.qq.com";
	private static final String DELIMITER = "&";
	private static final String SEPARATOR = "=";
	private static final String USER_SIG = "usersig";
	private static final String IDENTIFIER = "identifier";
	private static final String SDKAPPID = "sdkappid";
	private static final String RANDOM = "random";
	private static final String CONTENTTYPE = "contenttype";
	private static final String CONTENTTYPE_JSON = "json";

	private final Joiner.MapJoiner joiner = Joiner.on(DELIMITER).withKeyValueSeparator(SEPARATOR);
	private final ObjectMapper objectMapper = new ObjectMapper();

	private String identifier;
	private Long sdkappid;
	private long expire;
	private String sign;
	private TLSSigAPIv2 tlsSigAPIv2;
	private OkHttpClient okhttp3Client;

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
	 * @return
	 */
	private Map<String, String> getDefaultParams() {
		Map<String, String> pathParams = Maps.newHashMap();
		pathParams.put(USER_SIG, sign);
		pathParams.put(IDENTIFIER, identifier);
		pathParams.put(SDKAPPID, sdkappid.toString());
		pathParams.put(RANDOM, UUID.randomUUID().toString().replace("-", "").toLowerCase());
		pathParams.put(CONTENTTYPE, CONTENTTYPE_JSON);
		log.info("im参数   {}", pathParams);
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
			RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
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
	 * 1、导入单个帐号 v4/im_open_login_svc/account_import
	 * API：https://cloud.tencent.com/document/product/269/1608
	 * @param userId
	 * @param nickname
	 * @param avatar
	 * @return
	 */
	public IMActionResponse accountImport(String userId, String nickname, String avatar) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Identifier", getImUserByUserId(userId))
				.put("Nick", nickname)
				.put("FaceUrl", avatar).build();
		IMActionResponse res = request(TimApiAddress.ACCOUNT_IMPORT.getValue() + joiner.join(getDefaultParams()),
				requestBody, IMActionResponse.class);
		System.out.println(res);
		if (!res.isSuccess()) {
			log.error("导入信息失败, response message is: {}", res);
		}
		return res;
	}
	
	/**
	 * 2、导入多个帐号
	 * API：https://cloud.tencent.com/document/product/269/4919
	 * @author 		： <a href="https://github.com/vindell">vindell</a>
	 * @param userIds
	 * @return
	 */
	public IMActionResponse accountImport(String[] userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("Accounts", userIds).build();
		IMActionResponse res = request(TimApiAddress.MULTI_ACCOUNT_IMPORT.getValue() + joiner.join(getDefaultParams()),
				requestBody, IMActionResponse.class);
		System.out.println(res);
		if (!res.isSuccess()) {
			log.error("导入信息失败, response message is: {}", res);
		}
		return res;
	}
	
	/**
	 * 3、删除帐号
	 * API：https://cloud.tencent.com/document/product/269/36443
	 * @author 		： <a href="https://github.com/vindell">vindell</a>
	 * @param userIds
	 * @return
	 */
	public IMActionResponse accountDelete(String[] userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("DeleteItem", Stream.of(userIds).map(uid -> {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("UserID", uid);
					return userMap;
				}).collect(Collectors.toList())).build();
		IMActionResponse res = request(TimApiAddress.ACCOUNT_DELETE.getValue() + joiner.join(getDefaultParams()),
				requestBody, IMActionResponse.class);
		System.out.println(res);
		if (!res.isSuccess()) {
			log.error("导入信息失败, response message is: {}", res);
		}
		return res;
	}
	
	/**
	 * 4、查询帐号
	 * API：https://cloud.tencent.com/document/product/269/38417
	 * @author 		： <a href="https://github.com/vindell">vindell</a>
	 * @param userIds
	 * @return
	 */
	public AccountCheckActionResponse accountCheck(String[] userIds) {
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>()
				.put("CheckItem", Stream.of(userIds).map(uid -> {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("UserID", uid);
					return userMap;
				}).collect(Collectors.toList())).build();
		AccountCheckActionResponse res = request(TimApiAddress.ACCOUNT_DELETE.getValue() + joiner.join(getDefaultParams()),
				requestBody, AccountCheckActionResponse.class);
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
		Map<String, Object> requestBody = new ImmutableMap.Builder<String, Object>().put("To_Account", toAccount)
				.put("TagList", tagList).build();
		UserProfileItemResponse res = request(
				TimApiAddress.PORTRAIT_GET.getValue() + joiner.join(getDefaultParams()), requestBody,
				UserProfileItemResponse.class);
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
		if (userId == null || userId <= 0) {
			return res;
		}
		if (StringUtils.isBlank(nickname) && StringUtils.isBlank(avatar)) {
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
				.put("From_Account", String.valueOf(userId)).put("ProfileItem", objects).build();
		res = request(TimApiAddress.PORTRAIT_SET.getValue() + joiner.join(getDefaultParams()), requestBody,
				IMActionResponse.class);
		if (!res.isSuccess()) {
			log.error("设置资料失败, response message is: {}", res);
		}
		return res;
	}

	public Boolean addBlackList(String fromAccount, String toAccount) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setToAccount(Arrays.asList(toAccount));

		BlacklistResponse request = request(
				TimApiAddress.BLACK_LIST_ADD.getValue() + joiner.join(getDefaultParams()), message,
				BlacklistResponse.class);
		if (!request.isSuccess()) {
			log.error("拉黑失败, response message is: {}", request);
			return false;
		}
		return true;
	}

	public Boolean deleteBlackList(String fromAccount, String toAccount) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setToAccount(Arrays.asList(toAccount));

		BlacklistResponse request = request(
				TimApiAddress.BLACK_LIST_DELETE.getValue() + joiner.join(getDefaultParams()), message,
				BlacklistResponse.class);
		if (!request.isSuccess()) {
			log.error("取消拉黑失败, response message is: {}", request);
			return false;
		}
		return true;
	}

	public BlacklistResponse getBlackList(String fromAccount, Integer lastSequence) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setStartIndex(0);
		message.setMaxLimited(20);
		message.setLastSequence(lastSequence);

		BlacklistResponse blacklistResponse = request(
				TimApiAddress.BLACK_LIST_GET.getValue() + joiner.join(getDefaultParams()), message,
				BlacklistResponse.class);
		return blacklistResponse;
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
