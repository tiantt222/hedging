package com.hedging.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

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
import com.hedging.user.entity.Function;
import com.hedging.user.entity.User;
import com.hedging.user.service.IFunctionService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * 功能 Controller
 * 
 * @author tianwenlong
 */
@Controller
@RequestMapping("/api/function")
public class ApiFunctionController {

  @Resource
  private IFunctionService functionService;

  /**
   * 新增
   */
  @RequestMapping(method = RequestMethod.POST)
  @Accessable(name = "功能新增", function = "user", requireRole = true)
  public ModelAndView add(@RequestBody Function function) {
    this.functionService.add(function);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 新增(批量)
   */
  @RequestMapping(method = RequestMethod.POST, value = "/addDatas")
  @Accessable(name = "功能新增(批量)", function = "user", requireRole = true)
  public ModelAndView addDatas(List<Function> functionList) {
    this.functionService.add(functionList);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 修改
   */
  @RequestMapping(method = RequestMethod.PUT)
  @Accessable(name = "功能修改", function = "user", requireRole = true)
  public ModelAndView update(@RequestBody Function function) {
    this.functionService.update(function);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 修改(批量)
   */
  @RequestMapping(method = RequestMethod.PUT, value = "/updataDatas", produces = {"application/json;charset=UTF-8"})
  @Accessable(name = "功能新增(批量)", function = "user", requireRole = true)
  public ModelAndView updataDatas(@RequestBody List<Function> functionList) {
    this.functionService.update(functionList);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 查看
   */
  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  @ResponseBody
  @Accessable(name = "功能查看", function = "user", requireRole = true)
  public Function view(@PathVariable Long id) {
    return this.functionService.find(id);
  }

  /**
   * 删除
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  @Accessable(name = "功能删除", function = "user", requireRole = true)
  public ModelAndView delete(@PathVariable Long id) {
    this.functionService.delete(id);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 查询
   */
  @RequestMapping(method = RequestMethod.GET, value = "/search")
  @ResponseBody
  @Accessable(name = "功能查询", function = "user", requireRole = true)
  @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "pageSize", required = true, dataType = "int", paramType = "query")})
  public Pager<Function> search(@Valid Pager<Function> pager, Function function) {
    return this.functionService.search(pager, function);
  }

  /**
   * 查询
   */
  @RequestMapping(method = RequestMethod.GET, value = "/searchUserInfo")
  @ResponseBody
  @Accessable(name = "功能查询用户", function = "user", requireRole = true)
  @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "pageSize", required = true, dataType = "int", paramType = "query")})
  public Pager<User> searchUserInfo(Pager<User> pager, String functionCode) {
    return this.functionService.searchUser(pager, functionCode);
  }

  /**
   * 根据code查找
   */
  @RequestMapping(method = RequestMethod.GET, value = "/findByCode")
  @ResponseBody
  @Accessable(name = "findByCode")
  public Function findByCode(String code) {
    return this.functionService.find(code);
  }

}
