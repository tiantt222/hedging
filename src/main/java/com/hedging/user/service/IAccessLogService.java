package com.hedging.user.service;

import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.AccessLog;
import com.hedging.user.vo.AccessLogSearchVo;

/**
 * @author tianwenlong
 *
 */
public interface IAccessLogService {

  /**
   * 新增
   */
  public AccessLog add(AccessLog accessLog);

  /**
   * 按ID查找
   */
  public AccessLog find(Long id);

  /**
   * 按条件查找
   */
  public Pager<AccessLog> search(Pager<AccessLog> pager, AccessLogSearchVo accessLogSearchVo);

  /**
   * 获取日志环境变量
   */
  public String getEnableAccessLogProperties();

}
