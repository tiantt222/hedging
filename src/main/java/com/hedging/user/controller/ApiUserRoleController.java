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
import com.hedging.user.entity.User;
import com.hedging.user.entity.UserRole;
import com.hedging.user.service.IUserRoleService;
import com.hedging.user.vo.UserRoleSaveVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * 用户-角色 Controller
 * 
 * @author tianwenlong
 */
@Controller
@RequestMapping("/api/userRole")
public class ApiUserRoleController {

  @Resource
  private IUserRoleService userRoleService;

  /**
   * 新增
   */
  @RequestMapping(method = RequestMethod.POST)
  @Accessable(name = "用户-角色新增", function = "user", requireRole = true)
  public ModelAndView add(@RequestBody UserRole userRole) {
    this.userRoleService.add(userRole);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 新增(批量)
   */
  @RequestMapping(method = RequestMethod.POST, value = "/addDatas", produces = {"application/json;charset=UTF-8"})
  @Accessable(name = "用户-角色新增(批量)", function = "user", requireRole = true)
  public ModelAndView addDatas(@RequestBody List<UserRole> userRoleList) {
    this.userRoleService.add(userRoleList);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 修改
   */
  @RequestMapping(method = RequestMethod.PUT)
  @Accessable(name = "用户-角色修改", function = "user", requireRole = true)
  public ModelAndView update(@RequestBody UserRole userRole) {
    this.userRoleService.update(userRole);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 修改(批量)
   */
  @RequestMapping(method = RequestMethod.PUT, value = "/updateDatas", produces = {"application/json;charset=UTF-8"})
  @Accessable(name = "用户-角色修改(批量)", function = "user", requireRole = true)
  public ModelAndView updateDatas(@RequestBody List<UserRole> userRoleList) {
    this.userRoleService.update(userRoleList);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 查看
   */
  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  @ResponseBody
  @Accessable(name = "用户-角色查看", function = "user", requireRole = true)
  public UserRole view(@PathVariable Long id) {
    return this.userRoleService.find(id);
  }

  /**
   * 查询
   */
  @RequestMapping(method = RequestMethod.GET, value = "/search")
  @ResponseBody
  @Accessable(name = "用户-角色查询", function = "user", requireRole = true)
  @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "pageSize", required = true, dataType = "int", paramType = "query")})
  public Pager<UserRole> search(@Valid Pager<UserRole> pager, UserRole userRole) {
    return this.userRoleService.search(pager, userRole);
  }

  /**
   * 根据角色查找用户
   */
  @RequestMapping(method = RequestMethod.GET, value = "/searchUser")
  @ResponseBody
  @Accessable(name = "用户-角色根据角色查找用户", function = "user", requireRole = true)
  @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "pageSize", required = true, dataType = "int", paramType = "query")})
  public Pager<User> searchUser(@Valid Pager<User> pager, String roleCode) {
    return this.userRoleService.searchUser(pager, roleCode);
  }

  /**
   * 保存(用户-权限)
   */
  @RequestMapping(method = RequestMethod.POST, value = "/save")
  @Accessable(name = "用户-角色保存", function = "user", requireRole = true)
  public ModelAndView save(@RequestBody UserRoleSaveVo userRole) {
    this.userRoleService.save(userRole);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 删除用户的所有角色
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/deleteUserRoles/{userId}")
  @Accessable(name = "删除用户的所有角色", function = "user", requireRole = true)
  public ModelAndView deleteUserRoles(@PathVariable Long userId) {
    this.userRoleService.deleteUserRoles(userId);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 删除(批量)
   */
  @RequestMapping(method = RequestMethod.POST, value = "/deleteUserRolesI", produces = {
      "application/json;charset=UTF-8"})
  @Accessable(name = "用户-角色删除(批量)", function = "user", requireRole = true)
  public ModelAndView deleteUserRolesI(@RequestBody List<Long> id) {
    this.userRoleService.deleteUserRolesI(id);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

}
