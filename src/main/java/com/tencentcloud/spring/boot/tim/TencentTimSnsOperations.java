package com.tencentcloud.spring.boot.tim;

import java.util.Arrays;

import com.tencentcloud.spring.boot.tim.req.message.BlacklistMessage;
import com.tencentcloud.spring.boot.tim.resp.BlacklistResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Tim 资料管理接口集成
 * https://cloud.tencent.com/document/product/269/42440
 */
@Slf4j
public class TencentTimSnsOperations extends TencentTimOperations {

	public TencentTimSnsOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}
	
	public Boolean addBlackList(String fromAccount, String toAccount) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setToAccount(Arrays.asList(toAccount));

		BlacklistResponse res = request(
				TimApiAddress.BLACK_LIST_ADD.getValue() + joiner.join(getDefaultParams()), message,
				BlacklistResponse.class);
		if (!res.isSuccess()) {
			log.error("拉黑失败，ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
			return false;
		}
		return true;
	}

	public Boolean deleteBlackList(String fromAccount, String toAccount) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setToAccount(Arrays.asList(toAccount));

		BlacklistResponse res = request(
				TimApiAddress.BLACK_LIST_DELETE.getValue() + joiner.join(getDefaultParams()), message,
				BlacklistResponse.class);
		if (!res.isSuccess()) {
			log.error("取消拉黑失败，ActionStatus : {}, ErrorCode : {}, ErrorInfo : {}", res.getActionStatus(), res.getErrorCode(), res.getErrorInfo());
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
	
}
