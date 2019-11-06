package com.hedging.user.controller;

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
import com.hedging.user.entity.Role;
import com.hedging.user.service.IRoleService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * 角色 Controller
 * 
 * @author tianwenlong
 */
@Controller
@RequestMapping("/api/role")
public class ApiRoleController {

  @Resource
  private IRoleService roleService;

  /**
   * 新增
   */
  @RequestMapping(method = RequestMethod.POST)
  @Accessable(name = "角色新增", function = "user", requireRole = true)
  public ModelAndView add(@RequestBody Role role) {
    this.roleService.add(role);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 修改
   */
  @RequestMapping(method = RequestMethod.PUT)
  @Accessable(name = "角色修改", function = "user", requireRole = true)
  public ModelAndView update(@RequestBody Role role) {
    this.roleService.update(role);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 查看
   */
  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  @ResponseBody
  @Accessable(name = "角色查看", function = "user", requireRole = true)
  public Role view(@PathVariable Long id) {
    return this.roleService.find(id);
  }

  /**
   * 删除
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  @Accessable(name = "角色删除", function = "user", requireRole = true)
  public ModelAndView delete(@PathVariable Long id) {
    this.roleService.delete(id);

    ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
    mav.addObject("msg", "ok");
    return mav;
  }

  /**
   * 查询
   */
  @RequestMapping(method = RequestMethod.GET, value = "/search")
  @ResponseBody
  @Accessable(name = "角色查询", function = "user", requireRole = true)
  @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "pageSize", required = true, dataType = "int", paramType = "query")})
  public Pager<Role> search(@Valid Pager<Role> pager, Role role) {
    return this.roleService.search(pager, role);
  }

  /**
   * 根据code查找
   */
  @RequestMapping(method = RequestMethod.GET, value = "/findByCode")
  @ResponseBody
  @Accessable(name = "findByCode")
  public Role findByCode(String code) {
    return this.roleService.find(code);
  }

}
