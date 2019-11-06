package com.hedging.user.service;

import com.hedging.user.entity.UserApp;
import com.hedging.user.vo.AccessTokenRequest;
import com.hedging.user.vo.AccessTokenVo;

/**
 * @author tianwenlong
 *
 */
public interface IUserAppService {

	/**
	 * 按appId查找
	 */
	public UserApp findByAppId(String appId);

	/**
	 * 获取token
	 * 
	 * @param accessTokenRequest
	 * @return
	 */
	public AccessTokenVo getAccessToken(AccessTokenRequest accessTokenRequest);

}
