package com.hedging.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色-功能
 * 
 * @author tianwenlong
 */
@Accessors(chain = true)
@Data
@Entity
public class RoleFunction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 角色代码
	 */
	@Size(max = 30)
	private String roleCode;

	/**
	 * 功能代码
	 */
	@Size(max = 30)
	private String functionCode;

}
