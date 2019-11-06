package com.hedging.user.vo;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class RoleFunctionSaveVo {
	/**
	 * 角色代码
	 */
	private String roleCode;

	/**
	 * 功能代码
	 */
	private List<String> functionCode;

}
