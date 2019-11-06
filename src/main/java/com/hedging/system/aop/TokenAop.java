package com.hedging.system.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * token验证切面,方法第一参数要为token
 * 
 * @author tianwenlong
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TokenAop {

	String value() default "";

}
