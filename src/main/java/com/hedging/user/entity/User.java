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
import com.hedging.user.constant.UserStatusConstant;
import com.hedging.user.constant.UserTypeConstant;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户表
 * 
 * @author tianwenlong
 *
 */
@Accessors(chain = true)
@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 用户名
	 */
	@Size(max = 32)
	private String userName;

	/**
	 * 密码md5
	 */
	@Size(max = 32)
	private String password;

	/**
	 * 用户状态
	 * 
	 * @see UserStatusConstant
	 */
	@Size(max = 1)
	private String status;

	/**
	 * 邮箱
	 */
	@Size(max = 32)
	private String email;

	/**
	 * 电话
	 */
	@Size(max = 15)
	private String tel;

	/**
	 * 创建者用户ID
	 */
	private Long createUserId;

	/**
	 * 更新者用户ID
	 */
	private Long updateUserId;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**
	 * 用户类型
	 * 
	 * @see UserTypeConstant
	 */
	@Size(max = 1)
	private String type;

}