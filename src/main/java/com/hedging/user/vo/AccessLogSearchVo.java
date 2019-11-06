package com.hedging.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 日志查询VO
 *
 * @author tianwenlong
 */
@Accessors(chain = true)
@Data
@ApiModel
public class AccessLogSearchVo {

	/**
	 * 姓名
	 */
	@ApiModelProperty(required = false, value = "用户名", example = "田文龙")
	private String userName;

	/**
	 * 身份证号
	 */
	@ApiModelProperty(required = false, value = "用户id", example = "1")
	private Long idNumber;

	/**
	 * 用户IP
	 */
	@ApiModelProperty(required = false, value = "用户IP", example = "127.0.0.1")
	private String remoteIP;

	/**
	 * 操作名称
	 */
	@ApiModelProperty(required = false, value = "操作名称", example = "轨迹查询")
	private String actionName;

	/**
	 * 请求路径
	 */
	@ApiModelProperty(required = false, value = "请求路径", example = "kakou")
	private String requestPath;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(required = false, value = "开始时间", example = "2017-08-01 00:00:00")
	private String startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(required = false, value = "结束时间", example = "2017-08-01 23:00:00")
	private String endTime;

}
