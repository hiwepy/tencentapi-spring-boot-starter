# spring-boot-starter-baiduapi


#### 组件简介

> 基于 [百度智能云 - 人脸识别](https://cloud.baidu.com/product/face) 在线API实现的人脸识别整合
> 离线SDK参考 ：http://ai.baidu.com/tech/face/offline-sdk

#### 使用说明

##### 1、Spring Boot 项目添加 Maven 依赖

``` xml
<dependency>
	<groupId>${project.groupId}</groupId>
	<artifactId>spring-boot-starter-baiduapi</artifactId>
	<version>${project.version}</version>
</dependency>
```

##### 2、在`application.yml`文件中增加如下配置

```yaml
#################################################################################################
### 百度人脸识别 配置：
#################################################################################################
baidu:
  face:
    v3:
      enabled: true
      client-id: xxxx
      client-secret: xxxx
```

##### 3、使用示例

配置对象 FaceRecognitionProperties

```java
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(FaceRecognitionProperties.PREFIX)
public class FaceRecognitionProperties {

	public static final String PREFIX = "face";

	/**
	 * 人脸识别服务提供者（local: 本地实现, baidu: 百度人脸识别服务）
	 */
	private String provider = "local";
	/**
	 * 人脸识别数据分组（以学校代码为分组）
	 */
	private String group = "14535";

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
```

本地使用示接口（为了方便多种识别方案对接，这里采用了提供者模式进行开发）

```java
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

public interface FaceRecognitionProvider {

	/**
	 * Provider Name
	 * @return
	 */
	String getName();
	/**
	 * 人脸检测与属性分析：
	 * 1、人脸检测：检测图片中的人脸并标记出位置信息;
	 * 2、人脸关键点：展示人脸的核心关键点信息，及150个关键点信息。
	 * 3、人脸属性值：展示人脸属性信息，如年龄、性别等。
	 * 4、人脸质量信息：返回人脸各部分的遮挡、光照、模糊、完整度、置信度等信息。
	 * @param imageBase64 人脸图片文件
	 * @return
	 */
	JSONObject detect(String imageBase64) throws Exception;
	/**
	 * 活体检测:
	 * 1、人脸基础信息：包括人脸框位置，人脸空间旋转角度，人脸置信度等信息。
	 * 2、 人脸质量检测：判断人脸的遮挡、光照、模糊度、完整度等质量信息。可用于判断上传的人脸是否符合标准。
	 * 3、 基于图片的活体检测：基于单张图片，判断图片中的人脸是否为二次翻拍（举例：如用户A用手机拍摄了一张包含人脸的图片一，用户B翻拍了图片一得到了图片二，并用图片二伪造成用户A去进行识别操作，这种情况普遍发生在金融开户、实名认证等环节）。此能力可用于H5场景下的一些人脸采集场景中，增加人脸注册的安全性和真实性。
	 * @param image 人脸图片文件
	 * @return
	 */
	JSONObject verify(MultipartFile image) throws Exception;
	/**
	 * 人脸对比：
	 * 1、两张人脸图片相似度对比：比对两张图片中人脸的相似度，并返回相似度分值；
	 * 2、 多种图片类型：支持生活照、证件照、身份证芯片照、带网纹照四种类型的人脸对比；
	 * 3、活体检测控制：基于图片中的破绽分析，判断其中的人脸是否为二次翻拍（举例：如用户A用手机拍摄了一张包含人脸的图片一，用户B翻拍了图片一得到了图片二，并用图片二伪造成用户A去进行识别操作，这种情况普遍发生在金融开户、实名认证等环节。）；
	 * 4、质量检测控制：分析图片的中人脸的模糊度、角度、光照强度等特征，判断图片质量；
	 * @param userId 用户ID
	 * @param image 人脸图片文件
	 * @return
	 */
	JSONObject match(String userId, MultipartFile image) throws Exception;
	/**
	 * 人脸搜索：
	 * 1、人脸搜索：也称为1：N识别，在指定人脸集合中，找到最相似的人脸；
	 * 2、人脸搜索 M：N识别：也称为M：N识别，待识别图片中含有多个人脸时，在指定人脸集合中，找到这多个人脸分别最相似的人脸
	 * @param image 人脸图片文件
	 * @return
	 */
	JSONObject search(MultipartFile image) throws Exception;
	/**
	 * 人脸融合：对两张人脸进行融合处理，生成的人脸同时具备两张人脸的外貌特征（并不是换脸）
	 * 
	 * 1、指定人脸：当图片中有多张人脸时，可以指定某一张人脸与模板图进行融合
	 * 2、 图像融合：将检测到的两张人脸图片进行融合，输出一张融合后的人脸
	 * 3、 黄反识别：利用图像识别能力，判断图片中是否存在色情、暴恐血腥场景、政治敏感人物
	 * @param template 人脸融合模板图片文件
	 * @param target 人脸融合人脸图片文件
	 * @return
	 */
	JSONObject merge(MultipartFile template, MultipartFile target) throws Exception;
}
```

基于百度人脸识别接口实现：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ai.aip.spring.boot.FaceOption;
import com.baidu.ai.aip.spring.boot.FaceRecognitionV3Template;
import com.baidu.aip.util.Base64Util;

@Component
public class BaiduFaceRecognitionProvider implements FaceRecognitionProvider {

	@Autowired
	private FaceRecognitionV3Template faceRecognitionV3Template;
	@Autowired
	private FaceRecognitionProperties faceRecognitionProperties;
	@Autowired
	private IAuthzFaceDao authzFace;

	@Override
	public String getName() {
		return "baidu";
	}

	@Override
	public JSONObject detect(String imageBase64) throws Exception {
		return getFaceRecognitionV3Template().detect(imageBase64);
	}

	@Override
	public JSONObject verify(MultipartFile image) throws Exception {
		// 对文件进行转码
		String imageBase64 = Base64Util.encode(image.getBytes());
		return getFaceRecognitionV3Template().faceVerify(imageBase64, FaceOption.COMMON);
	}

	@Override
	public JSONObject match(String userId, MultipartFile image) throws Exception {
		AuthzFaceModel model = getAuthzFace().getModel(userId);
		String imageBase64_2 = Base64Util.encode(image.getBytes());
		return getFaceRecognitionV3Template().match(model.getFace(), imageBase64_2);
	}

	@Override
	public JSONObject search(MultipartFile image) throws Exception {
		// 对文件进行转码
		String imageBase64 = Base64Util.encode(image.getBytes());
		return getFaceRecognitionV3Template().search(imageBase64, faceRecognitionProperties.getGroup());
	}

	@Override
	public JSONObject merge(MultipartFile template, MultipartFile target) throws Exception {
		return getFaceRecognitionV3Template().merge(template.getBytes(), target.getBytes());
	}

	public FaceRecognitionV3Template getFaceRecognitionV3Template() {
		return faceRecognitionV3Template;
	}
}
```
