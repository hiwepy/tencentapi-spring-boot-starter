/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baidu.ai.aip.spring.boot;

import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ai.aip.utils.HttpUtil;
import com.baidu.aip.util.Base64Util;
import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * 
 * @author ： <a href="https://github.com/vindell">wandl</a>
 */

public class FaceRecognitionV2Template {

	public static final String FACE_DETECT_URL = "https://aip.baidubce.com/rest/2.0/face/v1/detect";
	public static final String FACE_MATCH_URL = "https://aip.baidubce.com/rest/2.0/face/v2/match";
	public static final String FACE_SEARCH_URL = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
	public static final String FACE_PERSON_VERIFY_URL = "https://aip.baidubce.com/rest/2.0/face/v3/person/verify";
	public static final String FACE_LIVENESS_VERIFY_URL = "https://aip.baidubce.com/rest/2.0/face/v3/faceverify";
	public static final String FACE_MERGE_URL = "https://aip.baidubce.com/rest/2.0/face/v1/merge";
	
	public static final String FACESET_USER_ADD_URL = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
	public static final String FACESET_USER_UPDATE_URL = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/update";
	public static final String FACESET_USER_DELETE_URL = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";
	public static final String FACESET_USER_GET_URL = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/get";
	public static final String FACESET_USER_GET_USERS_URL = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getusers";
	public static final String FACESET_GROUP_GET_LIST_URL = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getlist";
	public static final String FACESET_GROUP_ADDUSER_URL = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/adduser";
	public static final String FACESET_GROUP_DELETEUSER_URL = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/deleteuser";
	
	private MessageSourceAccessor messages = FaceRecognitionMessageSource.getAccessor();
	private FaceRecognitionV2Properties properties;
	
	public FaceRecognitionV2Template(FaceRecognitionV2Properties properties) {
		this.properties = properties;
	}
	
	protected JSONObject wrap(JSONObject result) {
		String error_code = result.getString("error_code");
		// 添加中文提示
		if(!StringUtils.equalsIgnoreCase(error_code, "0")) {
			// 获取异常信息
			String error_msg = messages.getMessage(error_code);
			result.put("error_msg", error_msg);
		} else {
			result.put("error_code", 0);
		}
		if(StringUtils.equalsIgnoreCase(error_code, "223120")) {
			result.put("liveness", 0);
		}
		return result;
	}
	
	/**
	 * 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
	 */
	private final LoadingCache<String, Optional<String>> ACCESS_TOKEN_CACHES = CacheBuilder.newBuilder()
			// 设置并发级别为8，并发级别是指可以同时写缓存的线程数
			.concurrencyLevel(8)
			// 设置写缓存后600秒钟过期
			.expireAfterWrite(29, TimeUnit.DAYS)
			// 设置缓存容器的初始容量为10
			.initialCapacity(2)
			// 设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
			.maximumSize(10)
			// 设置要统计缓存的命中率
			.recordStats()
			// 设置缓存的移除通知
			.removalListener(new RemovalListener<String, Optional<String>>() {
				@Override
				public void onRemoval(RemovalNotification<String, Optional<String>> notification) {
					System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
				}
			})
			// build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
			.build(new CacheLoader<String, Optional<String>>() {

				@Override
				public Optional<String> load(String keySecret) throws Exception {
					JSONObject key = JSONObject.parseObject(keySecret);
					String token = AuthClient.getAuth(key.getString("clientId"), key.getString("clientSecret"));
					return Optional.fromNullable(token);
				}
			});

	/**
	 * 
	 * 企业内部开发获取access_token 先从缓存查，再到百度查
	 * https://ai.baidu.com/docs#/Face-Detect-V3/top
	 * 
	 * @param clientId     官网获取的 API Key（百度云应用的AK）
	 * @param clientSecret 官网获取的 Secret Key（百度云应用的SK）
	 * @return
	 * @throws ExecutionException
	 */
	public String getAccessToken(String clientId, String clientSecret) throws ExecutionException {

		JSONObject key = new JSONObject();
		key.put("clientId", clientId);
		key.put("clientSecret", clientSecret);

		Optional<String> opt = ACCESS_TOKEN_CACHES.get(key.toJSONString());
		return opt.isPresent() ? opt.get() : null;

	}
	
	/**
	 * 人脸检测与属性分析：https://ai.baidu.com/docs#/Face-Detect/top
	 * 
	 * @param imageBytes       图片字节码：现支持PNG、JPG、JPEG、BMP，不支持GIF图片
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @return
	 */
	public JSONObject detect(byte[] imageBytes) {
		try {
			String imgStr = Base64Util.encode(imageBytes);
			return detect(imgStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 人脸检测与属性分析 ：https://ai.baidu.com/docs#/Face-Detect/top
	 * 
	 * @param imageBase64 Base64编码：请求的图片需经过Base64编码，图片的base64编码指将图片数据编码成一串字符串，使用该字符串代替图像地址。
	 *                    您可以首先得到图片的二进制，然后用Base64格式编码即可。需要注意的是，图片的base64编码是不包含图片头的，如data:image/jpg;base64,
	 *                    图片格式：现支持PNG、JPG、JPEG、BMP，不支持GIF图片
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @return
	 */
	public JSONObject detect(String imageBase64) {

		try {
            
            String imgParam = URLEncoder.encode(imageBase64, "UTF-8");

            String param = "max_face_num=" + properties.getMaxFaceNum() + "&face_fields=" + properties.getFaceFields() + "&image=" + imgParam;

			// 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
			String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

			String result = HttpUtil.post(FACE_DETECT_URL, accessToken, param);
			
			return wrap(JSONObject.parseObject(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject match(byte[] imageBytes_1, byte[] imageBytes_2) {
		return match(imageBytes_1, imageBytes_2, "faceliveness,faceliveness", "7,7");
	}
	
	public JSONObject match(byte[] imageBytes_1, byte[] imageBytes_2, String image_liveness) {
		return match(imageBytes_1, imageBytes_2, image_liveness, "7,7");
	}
	
	/**
	 * 人脸对比：https://ai.baidu.com/docs#/Face-Match/top
	 * 
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param imageBytes_1   图片字节码：现支持PNG、JPG、JPEG、BMP，不支持GIF图片
	 * @param imageBytes_2   图片字节码：现支持PNG、JPG、JPEG、BMP，不支持GIF图片
	 * @param image_liveness 返回的活体信息， “faceliveness,faceliveness”:表示对比对的两张图片都做活体检测；
	 *                       “,faceliveness”:表示对第一张图片不做活体检测、第二张图做活体检测；
	 *                       “faceliveness,”:表示对第一张图片做活体检测、第二张图不做活体检测；
	 *                       注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px*100px，人脸长宽与图片长宽比例，不小于1/3
	 * @param types          请求对比的两张图片的类型，示例：“7,13”
	 *                       7表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等
	 *                       11表示身份证芯片照：二代身份证内置芯片中的人像照片 12表示带水印证件照：一般为带水印的小图，如公安网小图
	 *                       13表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片，注：需要确保人脸部分不可太小，通常为100px*100px
	 * @return
	 */
	public JSONObject match(byte[] imageBytes_1, byte[] imageBytes_2, String image_liveness, String types) {
		String imageBase64_1 = Base64Util.encode(imageBytes_1);
		String imageBase64_2 = Base64Util.encode(imageBytes_2);
		return match(imageBase64_1, imageBase64_2, image_liveness, types);
	}
	
	public JSONObject match(String imageBase64_1, String imageBase64_2) {
		return match(imageBase64_1, imageBase64_2, "faceliveness,faceliveness", "7,7");
	}
	
	public JSONObject match(String imageBase64_1, String imageBase64_2, String image_liveness) {
		return match(imageBase64_1, imageBase64_2, image_liveness, "7,7");
	}
	
	/**
	 * 人脸对比：https://ai.baidu.com/docs#/Face-Match/top
	 * @author 		： <a href="https://github.com/vindell">wandl</a>
	 * @param imageBase64_1 Base64编码：请求的图片需经过Base64编码，图片的base64编码指将图片数据编码成一串字符串，使用该字符串代替图像地址。
	 *                    您可以首先得到图片的二进制，然后用Base64格式编码即可。需要注意的是，图片的base64编码是不包含图片头的，如data:image/jpg;base64,
	 *                    图片格式：现支持PNG、JPG、JPEG、BMP，不支持GIF图片
	 * @param imageBase64_2 Base64编码：请求的图片需经过Base64编码，图片的base64编码指将图片数据编码成一串字符串，使用该字符串代替图像地址。
	 *                    您可以首先得到图片的二进制，然后用Base64格式编码即可。需要注意的是，图片的base64编码是不包含图片头的，如data:image/jpg;base64,
	 *                    图片格式：现支持PNG、JPG、JPEG、BMP，不支持GIF图片
	 * @param image_liveness 返回的活体信息， “faceliveness,faceliveness”:表示对比对的两张图片都做活体检测；
	 *                       “,faceliveness”:表示对第一张图片不做活体检测、第二张图做活体检测；
	 *                       “faceliveness,”:表示对第一张图片做活体检测、第二张图不做活体检测；
	 *                       注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px*100px，人脸长宽与图片长宽比例，不小于1/3
	 * @param types          请求对比的两张图片的类型，示例：“7，13”
	 *                       7表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等
	 *                       11表示身份证芯片照：二代身份证内置芯片中的人像照片 12表示带水印证件照：一般为带水印的小图，如公安网小图
	 *                       13表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片，注：需要确保人脸部分不可太小，通常为100px*100px                    
	 * @return
	 */
	public JSONObject match(String imageBase64_1, String imageBase64_2, String image_liveness, String types) {
		
		try {
			
            String imgParam = URLEncoder.encode(imageBase64_1, "UTF-8");
            String imgParam2 = URLEncoder.encode(imageBase64_2, "UTF-8");

            String param = "image_liveness=" + image_liveness + "&types=" + types + "&images=" + imgParam + "," + imgParam2;

            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
         	String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACE_MATCH_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}

	/**
	 * 人脸搜索 : https://ai.baidu.com/docs#/Face-Search/top
	 * 
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param imageBytes  图片字节码：现支持PNG、JPG、JPEG、BMP，不支持GIF图片
	 * @param group_id_list 用户组id（由数字、字母、下划线组成），长度限制128B，如果需要查询多个用户组id，用逗号分隔
	 * @return
	 */
	public JSONObject search(byte[] imageBytes, String group_id_list) {
		String imageBase64 = Base64Util.encode(imageBytes);
		return search(imageBase64, group_id_list);
	}
	
	/**
	 * 人脸搜索 : https://ai.baidu.com/docs#/Face-Search/top
	 * 
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param imageBase64   Base64编码：请求的图片需经过Base64编码，图片的base64编码指将图片数据编码成一串字符串，使用该字符串代替图像地址。
	 *                      您可以首先得到图片的二进制，然后用Base64格式编码即可。需要注意的是，图片的base64编码是不包含图片头的，如data:image/jpg;base64,
	 *                      图片格式：现支持PNG、JPG、JPEG、BMP，不支持GIF图片
	 * @param group_id_list 用户组id（由数字、字母、下划线组成），长度限制128B，如果需要查询多个用户组id，用逗号分隔
	 * @return
	 */
	public JSONObject search(String imageBase64, String group_id_list) {
		
		try {
			
            String imgParam = URLEncoder.encode(imageBase64, "UTF-8");
            String param = "group_id=" + group_id_list + "&user_top_num=" + properties.getUserTopNum() + "&image=" + imgParam;

            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
         	String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACE_SEARCH_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}

	/**##########################人脸库管理########################### */
	
	/**
	 * 人脸注册： https://ai.baidu.com/docs#/Face-Set/36357792；
	 * 用于从人脸库中新增用户，可以设定多个用户所在组，及组内用户的人脸图片，典型应用场景：构建您的人脸库，如会员人脸注册，已有用户补全人脸信息等。
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param imageBase64      Base64编码：请求的图片需经过Base64编码，图片的base64编码指将图片数据编码成一串字符串，使用该字符串代替图像地址。
	 *                         您可以首先得到图片的二进制，然后用Base64格式编码即可。需要注意的是，图片的base64编码是不包含图片头的，如data:image/jpg;base64,
	 *                         图片格式：现支持PNG、JPG、JPEG、BMP，不支持GIF图片
	 * @param group_id         用户组id，标识一组用户（由数字、字母、下划线组成），长度限制48B。产品建议：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
	 * @param user_id          用户id（由数字、字母、下划线组成），长度限制128B
	 * @param user_info        用户资料，长度限制256B 默认空
	 * @return
	 */
	public JSONObject faceNew(String imageBase64, String group_id, String user_id, String user_info) {
		try {
			
            String imgParam = URLEncoder.encode(imageBase64, "UTF-8");
            String param = "uid=" + user_id + "&user_info=" + user_info + "&group_id=" + group_id + "&action_type=replace&image=" + imgParam ;

            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
         	String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACESET_USER_ADD_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 人脸更新：https://ai.baidu.com/docs#/Face-Set/115feb43；
	 * 用于对人脸库中指定用户，更新其下的人脸图像。
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param imageBase64      Base64编码：请求的图片需经过Base64编码，图片的base64编码指将图片数据编码成一串字符串，使用该字符串代替图像地址。
	 *                         您可以首先得到图片的二进制，然后用Base64格式编码即可。需要注意的是，图片的base64编码是不包含图片头的，如data:image/jpg;base64,
	 *                         图片格式：现支持PNG、JPG、JPEG、BMP，不支持GIF图片
	 * @param group_id         用户组id，标识一组用户（由数字、字母、下划线组成），长度限制48B。产品建议：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
	 * @param user_id          用户id（由数字、字母、下划线组成），长度限制128B
	 * @param user_info        用户资料，长度限制256B 默认空
	 * @return
	 */
	public JSONObject faceRenew(String imageBase64, String group_id, String user_id, String user_info) {
		try {
			
            String imgParam = URLEncoder.encode(imageBase64, "UTF-8");
            String param = "uid=" + user_id + "&user_info=" + user_info + "&group_id=" + group_id + "&action_type=replace&image=" + imgParam ;

            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
         	String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACESET_USER_UPDATE_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	

	/**
	 * 人脸删除：https://ai.baidu.com/docs#/Face-Set/13cf451f；
	 * 用于从人脸库中删除一个用户。
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param group_id         用户组id，标识一组用户（由数字、字母、下划线组成），长度限制48B。产品建议：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
	 * @param user_id          用户id（由数字、字母、下划线组成），长度限制128B
	 * @return
	 */
	public JSONObject faceDelete(String group_id, String user_id) {
		try {
			
            String param = "uid=" + user_id + "&group_id=" + group_id;

            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
         	String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACESET_USER_DELETE_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 用户信息查询：https://ai.baidu.com/docs#/Face-Set/49342029；
	 * 用于查询人脸库中某用户的详细信息。
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param group_id         用户组id，标识一组用户（由数字、字母、下划线组成），长度限制48B。产品建议：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
	 * @param user_id          用户id（由数字、字母、下划线组成），长度限制128B
	 * @return
	 */
	public JSONObject faceInfo(String group_id, String user_id) {
		try {
			
            String param = "uid=" + user_id + "&group_id=" + group_id;

            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
         	String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACESET_USER_GET_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	

	/**
	 * 组内用户列表查询： https://ai.baidu.com/docs#/Face-Set/edbb230c；
	 * 用于查询指定用户组中的用户列表。
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param group_id         用户组id(由数字、字母、下划线组成，长度限制48B)，如传入“@ALL”则从所有组中查询用户信息。注：处于不同组，但uid相同的用户，我们认为是同一个用户。
	 * @param start          	默认值0，起始序号
	 * @param num          	返回数量，默认值100，最大值1000
	 * @return
	 */
	public JSONObject faceUsers( String group_id, int start, int num) {
		 try {
			 
            String param = "group_id=" + group_id + "&start=" + Math.max(start, 0) + "&end=" + Math.max(num, 1000);

            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
            String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACESET_USER_GET_USERS_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 组列表查询：https://ai.baidu.com/docs#/Face-Set/57adbd34；
	 * 用于查询用户组的列表。
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param start        默认值0，起始序号
	 * @param num          回数量，默认值100，最大值1000
	 * @return
	 */
	public JSONObject groupList(int start, int num) {
		try {
			
            String param = "start=" + Math.max(start, 0) + "&num=" + num;
            
            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
         	String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACESET_GROUP_GET_LIST_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	
	/**
	 * 组间复制用户：https://ai.baidu.com/docs#/Face-Set/9004fef3；
	 * 用于将已经存在于人脸库中的用户复制到一个新的组。
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param group_id         用户组id(由数字、字母、下划线组成，长度限制48B)，如传入“@ALL”则从所有组中查询用户信息。注：处于不同组，但uid相同的用户，我们认为是同一个用户。
	 * @param user_id          用户id（由数字、字母、下划线组成），长度限制128B
	 * @param target_group_id  需要添加信息的组id，多个的话用逗号分隔
	 * @return
	 */
	public JSONObject userCopy( String group_id, String user_id, String target_group_id) {
		 try {
			 
            String param = "src_group_id=" + group_id + "&user_id=" + user_id + "&group_id=" + target_group_id;

            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
            String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACESET_GROUP_ADDUSER_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 组内删除用户：https://ai.baidu.com/docs#/Face-Set/9b46e037；
	 * 用于将用户从某个组中删除，但不会删除用户在其它组的信息。
	 * @author ： <a href="https://github.com/vindell">wandl</a>
	 * @param group_id         用户组id(由数字、字母、下划线组成，长度限制48B)，如传入“@ALL”则从所有组中查询用户信息。注：处于不同组，但uid相同的用户，我们认为是同一个用户。
	 * @param user_id          用户id（由数字、字母、下划线组成），长度限制128B
	 * @return
	 */
	public JSONObject userDelete( String group_id, String user_id) {
		 try {
			
            String param = "group_id=" + group_id + "&user_id=" + user_id;

            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
            String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACESET_GROUP_DELETEUSER_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	
	/**
	 * 身份验证：https://ai.baidu.com/docs#/Face-PersonVerify-V3/top
	 * @param imageBase64      图片信息(总数据大小应小于10M)，图片上传方式根据image_type来判断
	 * @param id_card_number   身份证号码
	 * @param name             姓名（真实姓名，和身份证号匹配）
	 * 
	 */
	public JSONObject personverify(String imageBase64, String id_card_number, String name) {
        try {
        	
        	String imgParam = URLEncoder.encode(imageBase64, "UTF-8");
    	  
        	String param = new StringBuilder().append("id_card_number=").append(id_card_number)
    			 .append("&name=").append(URLEncoder.encode(name, "UTF-8"))
    			 .append("&image=").append(imgParam).toString();

        	// 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
        	String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

        	String result = HttpUtil.post(FACE_PERSON_VERIFY_URL, accessToken, param);
        	
         
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
	* 在线活体检测：https://ai.baidu.com/docs#/Face-Liveness/top
	*/
	public JSONObject faceVerify(String imageBase64) {
       
        try {
        	
        	String imgParam = URLEncoder.encode(imageBase64, "UTF-8");
      	  
        	String param = new StringBuilder().append("max_face_num=").append(properties.getMaxFaceNum())
       			 .append("&face_fields=").append("faceliveness")
       			 .append("&image=").append(imgParam).toString();
        	
            // 注意：access_token的有效期为30天，切记需要每30天进行定期更换，或者每次请求都拉取新token；
         	String accessToken = getAccessToken(properties.getClientId(), properties.getClientSecret());

            String result = HttpUtil.post(FACE_LIVENESS_VERIFY_URL, accessToken, param);
            
            return wrap(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public FaceRecognitionV2Properties getProperties() {
		return properties;
	}

}
