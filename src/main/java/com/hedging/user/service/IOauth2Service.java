package com.hedging.user.service;

import com.hedging.user.vo.AccessTokenVo;

/**
 * @author tianwenlong
 */
public interface IOauth2Service {

	/**
	 * 获取token
	 * 
	 * @param token
	 * @return
	 */
	public AccessTokenVo getAccessTokenVo(String token);

	/**
	 * 通过Authorization Code获取Access Token
	 */
	public AccessTokenVo createAccessToken(String token, Long appId);

	/**
	 * 生成token
	 * 
	 * @param appKey
	 * @param appSecret
	 * @return
	 */
	public String genToken(String appKey, String appSecret);

	/**
	 * 清除token缓存
	 * 
	 * @param token
	 */
	public void evictAccessToken(String token);

	/**
	 * 清除tokenStr缓存
	 * 
	 * @param appKey
	 * @param appSecret
	 */
	public void evictTokenStr(String appKey, String appSecret);

}
