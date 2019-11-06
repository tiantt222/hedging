package com.hedging.user.service.impl;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dz.platform.system.util.Pager;
import com.hedging.user.dao.IAccessLogDao;
import com.hedging.user.entity.AccessLog;
import com.hedging.user.service.IAccessLogService;
import com.hedging.user.vo.AccessLogSearchVo;

/**
 * @author tianwenlong
 */
@Service("accessLogService")
public class AccessLogService implements IAccessLogService {

  @Resource
  private IAccessLogDao accessLogDao;

  @Resource
  private Environment env;

  @Override
  @Transactional
  public AccessLog add(AccessLog accessLog) {
    this.accessLogDao.add(accessLog);
    return accessLog;
  }

  @Override
  public AccessLog find(Long id) {
    return this.accessLogDao.find(AccessLog.class, id);
  }

  @Override
  public Pager<AccessLog> search(Pager<AccessLog> pager, AccessLogSearchVo accessLogSearchVo) {
    return this.accessLogDao.search(pager, accessLogSearchVo);
  }

  @Override
  public String getEnableAccessLogProperties() {
    return env.getProperty("app.enableAccessLog");
  }

}
