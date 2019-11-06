package com.hedging.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tianwenlong
 *
 */
@Accessors(chain = true)
@Data
public class UserLoginVo {

	@ApiModelProperty(required = true, value = "用户名")
	private String username;

	@ApiModelProperty(required = true, value = "密码", example = "123456")
	private String password;

	@ApiModelProperty(required = true, value = "用户类型", example = "1")
	private String type;

}
