package com.hedging.user.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 访问日志 entity
 * 
 * @author tianwenlong
 *
 */
@Accessors(chain = true)
@Data
@Entity
public class AccessLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 访问时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date accessTime;

	/**
	 * 姓名
	 */
	@Size(max = 30)
	private String userName;

	/**
	 * userId
	 */
	private Long idNumber;

	/**
	 * 会话ID
	 */
	@Size(max = 64)
	private String sessionId;

	/**
	 * 操作名称
	 */
	@Size(max = 60)
	private String actionName;

	/**
	 * 操作编号
	 */
	@Size(max = 30)
	private String actionCode;

	/**
	 * 用户IP
	 */
	@Size(max = 46)
	private String remoteIP;

	/**
	 * 服务器IP
	 */
	@Size(max = 46)
	private String localIP;

	/**
	 * 服务器端口
	 */
	@Size(max = 5)
	private String localPort;

	/**
	 * 请求URL
	 */
	@Size(max = 100)
	private String requestURL;

	/**
	 * 请求路径
	 */
	@Size(max = 100)
	private String requestPath;

	/**
	 * 请求路径
	 */
	@Size(max = 1000)
	private String requestQuery;

	/**
	 * 请求方法
	 */
	@Size(max = 10)
	private String requestMethod;

	/**
	 * 响应状态码
	 */
	@Size(max = 3)
	private String statusCode;

	/**
	 * 服务器端消耗时间(ms)
	 */
	private Integer serverTime;

}
