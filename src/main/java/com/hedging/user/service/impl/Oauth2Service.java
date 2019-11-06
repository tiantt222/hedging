package com.hedging.user.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.hedging.user.service.IOauth2Service;
import com.hedging.user.service.IUserAppService;
import com.hedging.user.vo.AccessTokenVo;

/**
 * @author tianwenlong
 */
@Service
public class Oauth2Service implements IOauth2Service {

	@Resource
	private Environment env;

	@Resource
	private IUserAppService userAppService;

	@Cacheable(cacheNames = "token", key = "#token")
	@Override
	public AccessTokenVo getAccessTokenVo(String token) {
		return new AccessTokenVo();
	}

	@Cacheable(cacheNames = "token", key = "#token")
	@Override
	public AccessTokenVo createAccessToken(String token, Long userId) {
		// 失效时间(s) 2小时
		long expires_in = Long.valueOf(env.getProperty("user.token.expires"));
		// 失效时间(ms, from 1970)
		long expires = System.currentTimeMillis() + expires_in * 1000;

		AccessTokenVo accessTokenVo = new AccessTokenVo();
		accessTokenVo.setAccess_token(token);
		accessTokenVo.setToken_type("bearer");
		accessTokenVo.setExpires_in(expires_in);
		accessTokenVo.setExpires(expires);
		accessTokenVo.setUserId(userId);
		return accessTokenVo;
	}

	@Cacheable(cacheNames = "tokenStr")
	@Override
	public String genToken(String appKey, String appSecret) {
		return UUID.randomUUID().toString();
	}

	@CacheEvict(cacheNames = "token", key = "#token")
	@Override
	public void evictAccessToken(String token) {

	}

	@CacheEvict(cacheNames = "tokenStr")
	@Override
	public void evictTokenStr(String appKey, String appSecret) {

	}

}
