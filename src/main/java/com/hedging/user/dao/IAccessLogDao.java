package com.hedging.user.dao;

import com.dz.platform.system.dao.IBaseDao;
import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.AccessLog;
import com.hedging.user.vo.AccessLogSearchVo;

/**
 * @author tianwenlong
 */
public interface IAccessLogDao extends IBaseDao {

  /**
   * 根据条件查找
   */
  public Pager<AccessLog> search(Pager<AccessLog> pager, AccessLogSearchVo accessLogSearchVo);

  /**
   * 增
   */
  public AccessLog add(AccessLog accessLog);

  /**
   * 按id查找
   */
  public AccessLog find(Class<AccessLog> findClass, Long id);

}
