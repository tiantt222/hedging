package com.hedging.user.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dz.platform.system.util.Pager;
import com.hedging.system.aop.Accessable;
import com.hedging.user.entity.AccessLog;
import com.hedging.user.service.IAccessLogService;
import com.hedging.user.vo.AccessLogSearchVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author tianwenlong
 */
@Controller
@RequestMapping("/api/accessLog")
public class ApiAccessLogController {

  @Resource
  private IAccessLogService accessLogService;

  /**
   * 查看
   */
  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  @ResponseBody
  @Accessable(name = "管理-日志详情查看", function = "user-accessLog", requireRole = true)
  @ApiOperation(value = "日志详情查看")
  public AccessLog view(@ApiParam(required = true, value = "日志id") @PathVariable Long id) {
    return this.accessLogService.find(id);
  }

  /**
   * 查询
   */
  @RequestMapping(method = RequestMethod.GET, value = "/search")
  @ResponseBody
  @Accessable(name = "管理-日志查询", function = "user-accessLog", requireRole = true)
  @ApiOperation(value = "日志条件查询")
  @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", required = true, dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "pageSize", required = true, dataType = "int", paramType = "query")})
  public Pager<AccessLog> search(@Valid Pager<AccessLog> pager, AccessLogSearchVo accessLogSearchVo) {
    return this.accessLogService.search(pager, accessLogSearchVo);
  }

}
