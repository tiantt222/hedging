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
 * 业务功能表
 * 
 * @author tianwenlong
 */
@Accessors(chain = true)
@Data
@Entity
public class Function {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 名称
	 */
	@Size(max = 60)
	private String name;

	/**
	 * 代码
	 */
	@Size(max = 60)
	private String code;

	/**
	 * 描述
	 */
	@Size(max = 300)
	private String description;

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

}
