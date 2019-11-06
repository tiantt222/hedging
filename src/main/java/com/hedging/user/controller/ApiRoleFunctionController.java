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
import com.hedging.user.entity.RoleFunction;
import com.hedging.user.service.IRoleFunctionService;
import com.hedging.user.vo.RoleFunctionSaveVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * 角色-功能 Controller
 * 
 * @author tianwenlong
 */
@Controller
@RequestMapping("/api/roleFunction")
public class ApiRoleFunctionController {

  @Resource
  private IRoleFunctionService roleFunctionService;

  /**
   * 新增
   */
  @RequestMapping(method = RequestMethod.POST)
  @Accessable(name = "角色-功能新增", function = "user", requireRole = true)
  public ModelAndView add(@RequestBody RoleFunction roleFunction) {
    this.roleFunctionService.add(roleFunction);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 新增(批量)
   */
  @RequestMapping(method = RequestMethod.POST, value = "/addDatas", produces = {"application/json;charset=UTF-8"})
  @Accessable(name = "角色-功能新增(批量)", function = "user", requireRole = true)
  public ModelAndView addDatas(@RequestBody List<RoleFunction> roleFunctionList) {
    this.roleFunctionService.add(roleFunctionList);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 修改
   */
  @RequestMapping(method = RequestMethod.PUT)
  @Accessable(name = "角色-功能修改", function = "user", requireRole = true)
  public ModelAndView update(@RequestBody RoleFunction roleFunction) {
    this.roleFunctionService.update(roleFunction);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 新增(批量)
   */
  @RequestMapping(method = RequestMethod.POST, value = "/updateDatas", produces = {"application/json;charset=UTF-8"})
  @Accessable(name = "角色-功能修改(批量)", function = "user", requireRole = true)
  public ModelAndView updateDatas(@RequestBody List<RoleFunction> roleFunctionList) {
    this.roleFunctionService.update(roleFunctionList);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 查看
   */
  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  @ResponseBody
  @Accessable(name = "角色-功能查看", function = "user", requireRole = true)
  public RoleFunction view(@PathVariable Long id) {
    return this.roleFunctionService.find(id);
  }

  /**
   * 删除
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  @Accessable(name = "角色-功能删除", function = "user", requireRole = true)
  public ModelAndView delete(@PathVariable Long id) {
    this.roleFunctionService.delete(id);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 查询
   */
  @RequestMapping(method = RequestMethod.GET, value = "/search")
  @ResponseBody
  @Accessable(name = "角色-功能查询", function = "user", requireRole = true)
  @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "pageSize", required = true, dataType = "int", paramType = "query")})
  public Pager<RoleFunction> search(@Valid Pager<RoleFunction> pager, RoleFunction roleFunction) {
    return this.roleFunctionService.search(pager, roleFunction);
  }

  /**
   * 保存(权限-功能)
   */
  @RequestMapping(method = RequestMethod.POST, value = "/save")
  @Accessable(name = "用户-角色保存", function = "user", requireRole = true)
  public ModelAndView save(@RequestBody RoleFunctionSaveVo roleFunction) {
    this.roleFunctionService.save(roleFunction);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 删除角色的所有功能
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/deleteRoleFunctions/{roleCode}")
  @Accessable(name = "删除角色的所有功能", function = "user", requireRole = true)
  public ModelAndView deleteRoleFunctions(@PathVariable String roleCode) {
    this.roleFunctionService.deleteRoleFunctions(roleCode);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 删除(批量)
   */
  @RequestMapping(method = RequestMethod.POST, value = "/deleteRoleFunctionsI", produces = {
      "application/json;charset=UTF-8"})
  @Accessable(name = "角色-功能删除(批量)", function = "user", requireRole = true)
  public ModelAndView deleteRoleFunctionsI(@RequestBody List<Long> id) {
    this.roleFunctionService.deleteRoleFunctionsI(id);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }
}
