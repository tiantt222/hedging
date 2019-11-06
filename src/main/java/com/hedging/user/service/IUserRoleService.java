package com.hedging.user.service;

import java.util.List;

import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.User;
import com.hedging.user.entity.UserRole;
import com.hedging.user.vo.UserRoleSaveVo;

/**
 * @author tianwenlong
 */
public interface IUserRoleService {

  /**
   * 新增
   */
  public UserRole add(UserRole userRole);

  /**
   * 新增(批量)
   */
  public void add(List<UserRole> userRoleList);

  /**
   * 修改
   */
  public UserRole update(UserRole userRole);

  /**
   * 修改(批量)
   */
  public void update(List<UserRole> userRoleList);

  /**
   * 按ID查找
   */
  public UserRole find(Long id);

  /**
   * 按条件查找
   */
  public Pager<UserRole> search(Pager<UserRole> pager, UserRole userRole);

  /**
   * 分页查询用户信息-角色
   */
  public Pager<User> searchUser(Pager<User> pager, String roleCode);

  /**
   * 查询所有的应用
   */
  public List<UserRole> list();

  /**
   * 保存用户 角色
   */
  public void save(UserRoleSaveVo userRoleSave);

  /**
   * 删除用户的所有角色
   */
  public void deleteUserRoles(Long userId);

  /**
   * 根据id删除角色
   */
  public void deleteUserRolesI(List<Long> id);

}
