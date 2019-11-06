package com.hedging.user.dao;

import java.util.List;

import com.dz.platform.system.dao.IBaseDao;
import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.User;
import com.hedging.user.entity.UserRole;

/**
 * @author tianwenlong
 */
public interface IUserRoleDao extends IBaseDao {

  /**
   * 分页查询用户-角色
   */
  public Pager<UserRole> search(Pager<UserRole> pager, UserRole userRole);

  /**
   * 根据ID删除角色
   */
  public void deleteByUserId(Long userId);

  /**
   * 根据ID批量删除角色
   */
  public void deleteByIds(List<Long> ids);

  /**
   * 分页查询用户信息-角色
   * 
   * @param pager
   * @param roleCode
   * @return
   */
  public Pager<User> searchUserInfo(Pager<User> pager, String roleCode);

}
