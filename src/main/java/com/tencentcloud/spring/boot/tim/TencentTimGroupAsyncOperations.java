/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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
package com.tencentcloud.spring.boot.tim;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

import com.tencentcloud.spring.boot.tim.req.message.BlacklistMessage;
import com.tencentcloud.spring.boot.tim.resp.BlacklistResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TencentTimGroupAsyncOperations extends TencentTimGroupOperations {

	public TencentTimGroupAsyncOperations(TencentTimTemplate timTemplate) {
		super(timTemplate);
	}
	
	public void asyncAddBlackList(String fromAccount, String toAccount) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setToAccount(Arrays.asList(toAccount));
		this.asyncRequest(TimApiAddress.BLACK_LIST_ADD.getValue() + joiner.join(getDefaultParams()), message, (response) -> {
			if (response.isSuccessful()) {
                try {
					String content = response.body().string();
					BlacklistResponse res = getTimTemplate().toBean(content, BlacklistResponse.class);
					if (res.isSuccess()) {
						log.debug("添加黑名单成功, response message is: {}", res);
					} else {
						log.error("添加黑名单失败, response message is: {}", res);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		});
	}

	public void asyncDeleteBlackList(String fromAccount, String toAccount) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setToAccount(Arrays.asList(toAccount));
		this.asyncRequest(TimApiAddress.BLACK_LIST_DELETE.getValue() + joiner.join(getDefaultParams()), message, (response) -> {
			if (response.isSuccessful()) {
                try {
					String content = response.body().string();
					BlacklistResponse res = getTimTemplate().toBean(content, BlacklistResponse.class);
					if (res.isSuccess()) {
						log.debug("移除黑名单成功, response message is: {}", res);
					} else {
						log.error("移除黑名单失败, response message is: {}", res);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		});
	}

	public void asyncGetBlackList(String fromAccount, Integer lastSequence, Consumer<BlacklistResponse> consumer) {
		BlacklistMessage message = new BlacklistMessage();
		message.setFromAccount(fromAccount);
		message.setStartIndex(0);
		message.setMaxLimited(20);
		message.setLastSequence(lastSequence);
		this.asyncRequest(TimApiAddress.BLACK_LIST_GET.getValue() + joiner.join(getDefaultParams()), message, (response) -> {
			if (response.isSuccessful()) {
                try {
					String content = response.body().string();
					BlacklistResponse res = getTimTemplate().toBean(content, BlacklistResponse.class);
					if (res.isSuccess()) {
						log.debug("获取黑名单成功, response message is: {}", res);
					} else {
						log.error("获取黑名单失败, response message is: {}", res);
					}
					consumer.accept(res);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		});
	}

}
