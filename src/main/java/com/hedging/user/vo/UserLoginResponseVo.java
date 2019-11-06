package com.hedging.user.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户登录返回VO
 * 
 * @author tianwenlong
 *
 */
@Accessors(chain = true)
@Data
public class UserLoginResponseVo {

	private Long id;

	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 电话
	 */
	private String tel;

	private String type;

}