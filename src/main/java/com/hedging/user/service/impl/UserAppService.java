package com.hedging.user.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hedging.system.exception.BusinessException;
import com.hedging.user.dao.IUserAppDao;
import com.hedging.user.entity.UserApp;
import com.hedging.user.service.IOauth2Service;
import com.hedging.user.service.IUserAppService;
import com.hedging.user.vo.AccessTokenRequest;
import com.hedging.user.vo.AccessTokenVo;

/**
 * @author tianwenlong
 *
 */
@Service
public class UserAppService implements IUserAppService {

	@Resource
	private IUserAppDao userAppDao;

	@Resource
	private IOauth2Service oauth2Service;

	@Override
	public UserApp findByAppId(String appId) {
		return this.userAppDao.findByAppId(appId);
	}

	@Override
	public AccessTokenVo getAccessToken(AccessTokenRequest accessTokenRequest) {
		String grantType = accessTokenRequest.getGrant_type();
		String appKey = accessTokenRequest.getClient_id();
		String appSecret = accessTokenRequest.getClient_secret();

		if (grantType == null || appKey == null || appSecret == null) {
			throw new BusinessException("invalid_request");
		}
		if (!"password".equals(grantType)) {
			throw new BusinessException("unsupported_grant_type");
		}

		UserApp application = this.findByAppId(appKey);
		if (application == null) {
			throw new BusinessException("invalid_client");
		}
		if (!application.getAppSecret().equals(appSecret)) {
			throw new BusinessException("invalid_client");
		}

		String token = this.oauth2Service.genToken(appKey, appSecret);

		AccessTokenVo vo = this.oauth2Service.createAccessToken(token, application.getId());
		if (new Date().getTime() > vo.getExpires()) {
			this.oauth2Service.evictAccessToken(token);
			this.oauth2Service.evictTokenStr(appKey, appSecret);

			token = this.oauth2Service.genToken(appKey, appSecret);
			vo = this.oauth2Service.createAccessToken(token, application.getId());

		}
		return vo;
	}

}
