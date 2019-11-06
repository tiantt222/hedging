package com.hedging.user.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * AuthorizationCode VO
 * 
 * @author tianwenlong
 */
@Accessors(chain = true)
@Data
public class AccessTokenVo {

	/**
	 * 授权令牌
	 */
	private String access_token;

	/**
	 * 令牌类型
	 */
	private String token_type;

	/**
	 * access token的有效期，单位为秒。
	 */
	private long expires_in;

	/**
	 * 失效时间(ms, from 1970)
	 */
	private long expires;

	/**
	 * 用户ID
	 */
	private Long userId;

}
