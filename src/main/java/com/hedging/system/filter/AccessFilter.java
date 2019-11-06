package com.hedging.system.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.hedging.user.constant.LoginConstant;
import com.hedging.user.context.FunctionContextHolder;
import com.hedging.user.context.UserContextHolder;
import com.hedging.user.entity.AccessLog;
import com.hedging.user.entity.Function;
import com.hedging.user.entity.User;
import com.hedging.user.service.IAccessLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tianwenlong
 *
 */
@Slf4j
public class AccessFilter implements Filter {

	@Autowired
	private IAccessLogService accessLogService;

	private final Executor executor = Executors.newFixedThreadPool(5);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();

		long startTime = System.currentTimeMillis();

		try {
			User user = (User) session.getAttribute(LoginConstant.SESSION_USER);

			if (user != null) {
				UserContextHolder.setCurrentUser(user);
			}

			// 权限控制由注解实现

			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			log.error("Error", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			// 记录日志
			Function function = FunctionContextHolder.getFunction();
			if ("true".equals(accessLogService.getEnableAccessLogProperties()) && function != null) {
				try {

					AccessLog accessLog = new AccessLog();

					accessLog.setAccessTime(new Date(startTime));

					// 记录用户信息
					User user = UserContextHolder.getCurrentUser();
					if (user != null) {
						accessLog.setUserName(user.getUserName());
						accessLog.setIdNumber(user.getId());
					}

					accessLog.setSessionId(session.getId());

					// 记录用户操作的功能名称和编码
					accessLog.setActionName(function.getName());
					accessLog.setActionCode(function.getCode());
					FunctionContextHolder.clear();

					// 用户IP地址 因为有nginx代理无法使用request.getRemoteAddr()
					accessLog.setRemoteIP(request.getHeader("x-forwarded-for"));
					accessLog.setLocalIP(request.getLocalAddr());
					accessLog.setLocalPort(String.valueOf(request.getLocalPort()));

					accessLog.setRequestMethod(request.getMethod());
					accessLog.setRequestURL(request.getRequestURL().toString());
					accessLog.setRequestPath(request.getServletPath());
					String queryString = request.getQueryString();
					if (queryString != null) {
						accessLog.setRequestQuery(URLDecoder.decode(queryString, "UTF-8"));
					}
					accessLog.setStatusCode(String.valueOf(response.getStatus()));
					long endTime = System.currentTimeMillis();
					accessLog.setServerTime(Integer.parseInt(String.valueOf(endTime - startTime)));

					executor.execute(new Runnable() {
						@Override
						public void run() {
							accessLogService.add(accessLog);
						}
					});
				} catch (Exception e) {
					log.error("访问日志记录发生错误", e);
				}
			}
			UserContextHolder.clear();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void destroy() {
		UserContextHolder.clear();
	}

}
