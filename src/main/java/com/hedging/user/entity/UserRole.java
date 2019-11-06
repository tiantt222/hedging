package com.hedging.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户-角色
 * 
 * @author tianwenlong
 */
@Accessors(chain = true)
@Data
@Entity
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 角色代码
	 */
	@Size(max = 30)
	private String roleCode;

}
