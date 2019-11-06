package com.hedging.user.dao;

import com.dz.platform.system.dao.IBaseDao;
import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.Role;

/**
 * @author tianwenlong
 */
public interface IRoleDao extends IBaseDao {

  /**
   * 按代码查找
   */
  public Role find(String code);

  /**
   * 按条件查找
   */
  public Pager<Role> search(Pager<Role> pager, Role role);

  /**
   * 按角色名查找
   */
  public Role findByName(String name);

}
