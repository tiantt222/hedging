package com.hedging.system.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import com.hedging.system.util.ApplicationContextUtils;

/**
 * @author tianwenlong
 *
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 8518330570466578282L;

	private static final MessageSource a = ApplicationContextUtils.getBean("messageSource", MessageSource.class);
	private String b;
	private String c;

	public String getCode() {
		return b;
	}

	public String getMessage() {
		return c;
	}

	public BusinessException(String paramString) {
		b = paramString;
		try {
			c = a.getMessage(paramString, null, Locale.getDefault());
		} catch (NoSuchMessageException localNoSuchMessageException) {
			c = paramString;
		}
	}

	public BusinessException(String paramString, Object... paramVarArgs) {
		b = paramString;
		c = a.getMessage(paramString, paramVarArgs, Locale.getDefault());
	}
}
