package com.hedging.system.aop;

import java.util.Date;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.hedging.system.exception.BusinessException;
import com.hedging.user.service.IOauth2Service;
import com.hedging.user.vo.AccessTokenVo;

/**
 * @author tianwenlong
 */
@Aspect
@Component
public class TokenAspect {

	@Resource
	private IOauth2Service oauth2Service;

	@Before("@annotation(TokenAop)")
	public void access(JoinPoint jp) {
		AccessTokenVo at = oauth2Service.getAccessTokenVo(jp.getArgs()[0].toString());

		if (new Date().getTime() > at.getExpires()) {
			throw new BusinessException("token.expire.error");
		}
	}

}
