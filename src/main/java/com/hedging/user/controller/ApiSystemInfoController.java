package com.hedging.user.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hedging.system.constant.ViewNameConstant;
import com.hedging.user.entity.SystemInfo;
import com.hedging.user.service.ISystemInfoService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Zhao JinMing
 *
 */
@Controller
@RequestMapping("/api/systemInfo")
public class ApiSystemInfoController {

	@Resource
	private ISystemInfoService systemInfoService;

	/**
	 * 新增信息
	 * 
	 * @param systemInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/addSystemInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "添加系统信息")
	public ModelAndView addSystemInfo(@RequestBody SystemInfo systemInfo) {
		systemInfoService.addSystemInfo(systemInfo);
		ModelAndView mav = new ModelAndView(ViewNameConstant.JSON_VIEW);
		mav.addObject("msg", "ok");
		return mav;
	}

	/**
	 * 删除信息
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "删除系统信息")
	public ModelAndView deleteSystemInfo(@PathVariable Long id) {
		systemInfoService.deleteSystemInfo(id);
		ModelAndView mav = new ModelAndView(ViewNameConstant.JSON_VIEW);
		mav.addObject("msg", "ok");
		return mav;
	}

	/**
	 * 修改信息
	 * 
	 * @param systemInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/updateSystemInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "修改系统信息")
	public ModelAndView updateSystemInfo(@RequestBody SystemInfo systemInfo) {
		systemInfoService.updateSystemInfo(systemInfo);
		ModelAndView mav = new ModelAndView(ViewNameConstant.JSON_VIEW);
		mav.addObject("msg", "ok");
		return mav;
	}

	/**
	 * 查找所有
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取所有系统信息")
	public List<SystemInfo> searchAll() {
		return systemInfoService.searchAll();
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "按id查找")
	public SystemInfo findById(@PathVariable Long id) {
		return systemInfoService.findById(id);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/fetchVersion", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "查找当前系统版本")
	public ModelAndView fetchCurrentVersion() {
		String result = systemInfoService.fetchCurrentVersion();
		ModelAndView mav = new ModelAndView(ViewNameConstant.JSON_VIEW);
		mav.addObject("msg", result);
		return mav;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/getAllSort", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取排序后所有")
	public List<SystemInfo> getAllSort() {
		return systemInfoService.getAllSort();
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/fetchCurrentLog", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取当前版本更新日志")
	public ModelAndView fetchCurrentLog() {
		String result = systemInfoService.fetchCurrentLog();
		ModelAndView mav = new ModelAndView(ViewNameConstant.JSON_VIEW);
		mav.addObject("msg", result);
		return mav;
	}
}
