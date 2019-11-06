package com.hedging.user.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 重置密码VO
 * 
 * @author tianwenlong
 */
@Accessors(chain = true)
@Data
public class ResetPasswordVo {

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 密码确认
	 */
	private String passwordConfirm;

}
