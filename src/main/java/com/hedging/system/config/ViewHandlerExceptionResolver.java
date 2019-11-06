package com.hedging.system.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.util.WebUtils;

import com.dz.platform.system.exception.AccessException;
import com.hedging.system.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ViewHandlerExceptionResolver implements HandlerExceptionResolver {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ModelAndView resolveException(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, Object paramObject, Exception paramException) {
		ModelAndView localModelAndView = null;
		if ((paramException instanceof BusinessException)) {
			localModelAndView = new ModelAndView(new MappingJackson2JsonView());
			localModelAndView.addObject("msg", paramException.getMessage());
			if (!WebUtils.isIncludeRequest(paramHttpServletRequest)) {
				paramHttpServletResponse.setStatus(400);
				paramHttpServletRequest.setAttribute("javax.servlet.error.status_code", Integer.valueOf(400));
			}
		} else {
			Object localObject;
			if ((paramException instanceof AccessException)) {
				localObject = (AccessException) paramException;
				if (((AccessException) localObject).getRedirectUrl() != null) {
					localModelAndView = new ModelAndView("redirect:" + ((AccessException) localObject).getRedirectUrl());
				} else {
					localModelAndView = new ModelAndView(new MappingJackson2JsonView());
					localModelAndView.addObject("code", ((AccessException) localObject).getStatusCode());
					paramHttpServletResponse.setStatus(((AccessException) localObject).getStatusCode().intValue());
					paramHttpServletRequest.setAttribute("javax.servlet.error.status_code",
							((AccessException) localObject).getStatusCode());
				}
			} else if ((paramException instanceof BindException)) {
				log.warn("bindException", paramException);
				localModelAndView = new ModelAndView(new MappingJackson2JsonView());
				localModelAndView.addObject("code", "bindException");
				localObject = (BindException) paramException;
				if (((BindException) localObject).hasGlobalErrors()) {
					localModelAndView.addObject("msg", ((BindException) localObject).getGlobalError().getObjectName() + ": "
							+ ((BindException) localObject).getGlobalError().getDefaultMessage());
				} else if (((BindException) localObject).hasFieldErrors()) {
					localModelAndView.addObject("msg", ((BindException) localObject).getFieldError().getField() + ": "
							+ ((BindException) localObject).getFieldError().getDefaultMessage());
				}
				if (!WebUtils.isIncludeRequest(paramHttpServletRequest)) {
					paramHttpServletResponse.setStatus(400);
					paramHttpServletRequest.setAttribute("javax.servlet.error.status_code", Integer.valueOf(400));
				}
			} else if ((paramException instanceof ServletException)) {
				log.error("servletException", paramException);
				localModelAndView = new ModelAndView(new MappingJackson2JsonView());
				localModelAndView.addObject("code", "servletException");
				localModelAndView.addObject("msg",
						"The request url is not existed. " + paramHttpServletRequest.getRequestURL());
				if (!WebUtils.isIncludeRequest(paramHttpServletRequest)) {
					paramHttpServletResponse.setStatus(400);
					paramHttpServletRequest.setAttribute("javax.servlet.error.status_code", Integer.valueOf(400));
				}
			} else {
				log.error("unknowError", paramException);
				localModelAndView = new ModelAndView(new MappingJackson2JsonView());
				localModelAndView.addObject("code", "unknowError");
				localModelAndView.addObject("msg", paramException.getMessage());
				if (!WebUtils.isIncludeRequest(paramHttpServletRequest)) {
					paramHttpServletResponse.setStatus(400);
					paramHttpServletRequest.setAttribute("javax.servlet.error.status_code", Integer.valueOf(400));
				}
			}
		}
		return localModelAndView;
	}
}
