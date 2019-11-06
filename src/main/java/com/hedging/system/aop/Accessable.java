package com.hedging.system.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tianwenlong
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Accessable {

	/**
	 * 名称
	 */
	String name() default "";

	/**
	 * 功能Code
	 */
	String function() default "";

	/**
	 * 是否要求角色
	 */
	boolean requireRole() default false;

	/**
	 * 是否要求登录
	 */
	boolean requireLogin() default true;

	/**
	 * 是否记录日志
	 */
	boolean log() default true;

}
