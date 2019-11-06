package com.hedging.user.dao;

import java.util.List;

import com.dz.platform.system.dao.IBaseDao;
import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.RoleFunction;

/**
 * @author tianwenlong
 */
public interface IRoleFunctionDao extends IBaseDao {

  /**
   * 按条件查找
   */
  public Pager<RoleFunction> search(Pager<RoleFunction> pager, RoleFunction roleFunction);

  /**
   * 根据ID删除角色功能
   */
  public void deleteByRoleCode(String roleCode);

  /**
   * 根据ID批量删除角色功能
   */
  public void deleteByIds(List<Long> ids);

}
