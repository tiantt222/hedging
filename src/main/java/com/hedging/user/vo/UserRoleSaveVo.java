package com.hedging.user.vo;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserRoleSaveVo {

	private Long userId;

	/**
	 * 角色代码
	 */
	private List<String> roleCode;

}
