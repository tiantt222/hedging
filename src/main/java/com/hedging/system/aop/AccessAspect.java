package com.hedging.system.aop;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dz.platform.system.exception.AccessException;
import com.dz.platform.system.util.RequestUtils;
import com.hedging.user.context.FunctionContextHolder;
import com.hedging.user.context.UserContextHolder;
import com.hedging.user.entity.Function;
import com.hedging.user.entity.User;
import com.hedging.user.service.IUserService;

/**
 * @author tianwenlong
 */
@Aspect
@Component
public class AccessAspect {

	@Resource
	private IUserService userService;

	@Before("@annotation(accessable)")
	public void access(Accessable accessable) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();

		if (accessable.log()) {
			// 记录当前请求的功能名称和编码，在AccessFilter里日志记录使用
			Function function = new Function();
			function.setName(accessable.name());
			function.setCode(accessable.function());
			FunctionContextHolder.setFunction(function);
		}

		// 登录校验
		if (accessable.requireLogin() || accessable.requireRole()) {
			User user = UserContextHolder.getCurrentUser();
			if (user == null) {
				if (RequestUtils.isAjax(request)) {
					// Ajax请求
					throw new AccessException(HttpServletResponse.SC_UNAUTHORIZED);
				} else {
					// 暂时不支持页面请求
					throw new AccessException(HttpServletResponse.SC_UNAUTHORIZED);
				}
			}
		}

		// 角色校验
		if (accessable.requireRole()) {
			User user = UserContextHolder.getCurrentUser();
			boolean isRoleValid = false;

			Set<String> functionCodes = this.userService.listFunctions(user);
			if (functionCodes.contains(accessable.function())) {
				isRoleValid = true;
			}

			if (!isRoleValid) {
				throw new AccessException(HttpServletResponse.SC_FORBIDDEN);
			}
		}
	}

}
