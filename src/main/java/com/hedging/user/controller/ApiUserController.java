package com.hedging.user.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.dz.platform.system.util.Pager;
import com.hedging.system.aop.Accessable;
import com.hedging.system.exception.BusinessException;
import com.hedging.user.constant.UserStatusConstant;
import com.hedging.user.context.UserContextHolder;
import com.hedging.user.entity.User;
import com.hedging.user.service.IUserAppService;
import com.hedging.user.service.IUserService;
import com.hedging.user.vo.AccessTokenRequest;
import com.hedging.user.vo.AccessTokenVo;
import com.hedging.user.vo.ResetPasswordVo;
import com.hedging.user.vo.UserLoginResponseVo;
import com.hedging.user.vo.UserLoginVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author tianwenlong
 *
 */
@Controller
@RequestMapping("/api/user")
public class ApiUserController {

	@Resource
	private IUserService userService;

	@Resource
	private IUserAppService userAppService;

	/**
	 * 获取当前用户
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/loginUser")
	@ResponseBody
	@Accessable(log = false)
	@ApiOperation(value = "获取当前用户")
	public User loginUser() {
		return UserContextHolder.getCurrentUser();
	}

	/**
	 * 登录
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	@ResponseBody
	@Accessable(name = "登录系统", requireLogin = false)
	@ApiOperation(value = "根据用户名获取用户对象")
	public UserLoginResponseVo login(@RequestBody UserLoginVo userLoginVo, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		User user = this.userService.login(userLoginVo);

		UserLoginResponseVo result = new UserLoginResponseVo();
		BeanUtils.copyProperties(user, result);

		this.userService.processLogin(session, request, response, user);

		return result;
	}

	/**
	 * 退出系统
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/logout")
	@Accessable(name = "退出系统")
	@ApiOperation(value = "退出系统")
	public ModelAndView logout(HttpSession session, HttpServletResponse response) {
		session.invalidate();

		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		mav.addObject("msg", "ok");
		return mav;
	}

	/**
	 * 获取当前用户功能权限
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/listFunctions")
	@ResponseBody
	@Accessable(requireLogin = true, log = false)
	@ApiOperation(value = "获取当前用户功能权限")
	public Set<String> listFunctions() {
		User user = UserContextHolder.getCurrentUser();
		return this.userService.listFunctions(user);
	}

	/**
	 * 新增
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	@Accessable(name = "用户新增", function = "user", requireRole = true)
	public ModelAndView add(@RequestBody User user) {
		this.userService.add(user);

		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		mav.addObject("msg", "ok");
		return mav;
	}

	/**
	 * 修改
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@Accessable(name = "用户修改", function = "user", requireRole = true)
	public ModelAndView update(@RequestBody User user) {
		this.userService.update(user);

		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		mav.addObject("msg", "ok");
		return mav;
	}

	/**
	 * 重置用户密码
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "resetPassword")
	@Accessable(name = "重置用户密码", function = "user")
	public ModelAndView resetPassword(@RequestBody ResetPasswordVo resetPasswordVo) {
		this.userService.modifyPassword(resetPasswordVo);

		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		mav.addObject("msg", "ok");
		return mav;
	}

	/**
	 * 查看
	 */
	@RequestMapping(method = RequestMethod.GET, value = "find/{id}")
	@ResponseBody
	@Accessable(name = "用户查看", function = "user")
	public User view(@PathVariable Long id) {
		return this.userService.find(id);
	}

	/**
	 * 查询
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/search")
	@ResponseBody
	@Accessable(name = "用户查询")
	@ApiImplicitParams({@ApiImplicitParam(name = "pageNum", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", required = true, dataType = "int", paramType = "query")})
	public Pager<User> search(@Valid Pager<User> pager, User user) {

		return this.userService.search(pager, user);
	}

	/**
	 * 激活
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/enable")
	@ResponseBody
	@Accessable(name = "用户激活", function = "user", requireRole = true)
	public ModelAndView enable(Long userId) {
		System.out.println(userId);
		this.userService.changeStatus(userId, UserStatusConstant.ENABLED);

		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		mav.addObject("msg", "ok");
		return mav;
	}

	/**
	 * 删除
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
	@ResponseBody
	@Accessable(name = "用户删除", function = "user", requireRole = true)
	public ModelAndView delete(@PathVariable Long id) {
		this.userService.delete(id);

		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		mav.addObject("msg", "ok");
		return mav;
	}

	/**
	 * 禁用
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/disable")
	@ResponseBody
	@Accessable(name = "用户禁用", function = "user", requireRole = true)
	public ModelAndView disable(Long userId) {
		this.userService.changeStatus(userId, UserStatusConstant.DISABLED);

		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
		mav.addObject("msg", "ok");
		return mav;
	}

	/**
	 * 根据用户名查找
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/findByUsername")
	@ResponseBody
	@Accessable(name = "根据用户名查找")
	public User findByUsername(String username) {
		return this.userService.findByUsername(username);
	}

	/**
	 * 获取Access Token
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getToken")
	@ResponseBody
	public ModelAndView getToken(AccessTokenRequest accessTokenRequest, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());

		try {
			AccessTokenVo accessTokenVo = this.userAppService.getAccessToken(accessTokenRequest);

			mav.addObject("access_token", accessTokenVo.getAccess_token());
			mav.addObject("token_type", accessTokenVo.getToken_type());
			mav.addObject("expires_in", accessTokenVo.getExpires_in());
			mav.addObject("success", 0);
			mav.addObject("msg", "调用成功");
		} catch (BusinessException e) {
			mav.addObject("success", -1);
			mav.addObject("msg", e.getCode());
		}

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");

		return mav;
	}

}
